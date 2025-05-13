package br.ufrn.imd.cbm.services;

import br.ufrn.imd.cbm.configs.SecurityConfiguration;
import br.ufrn.imd.cbm.dtos.CreateUserDto;
import br.ufrn.imd.cbm.dtos.LoginUserDto;
import br.ufrn.imd.cbm.dtos.RecoveryJwtTokenDto;
import br.ufrn.imd.cbm.models.Role;
import br.ufrn.imd.cbm.models.User;
import br.ufrn.imd.cbm.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    // Método responsável por autenticar um usuário e retornar um token JWT
    public RecoveryJwtTokenDto authenticateUser(LoginUserDto loginUserDto) {
        // Cria um objeto de autenticação com o email e a senha do usuário
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());

        // Autentica o usuário com as credenciais fornecidas
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // Obtém o objeto UserDetails do usuário autenticado
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Gera um token JWT para o usuário autenticado
        return new RecoveryJwtTokenDto(jwtTokenService.generateToken(userDetails));
    }

    public void createUser(CreateUserDto createUserDto) {

        User newUser = User.builder()
                .name(createUserDto.name())
                .email(createUserDto.email())
                .password(securityConfiguration.passwordEncoder().encode(createUserDto.password()))
                .roles(List.of(Role.builder().name(createUserDto.role()).build()))
                .orders(new ArrayList<>())
                .addresses(new ArrayList<>())
                .build();

        userRepository.save(newUser);
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email não encontrado"));
    }

    public void updateUserById(Long id, CreateUserDto createUserDto) {
        User user = findUserById(id);
        if (user == null) {
            throw new RuntimeException("Usuário não encontrado");
        }
        if (createUserDto.name() != null) user.setName(createUserDto.name());
        if (createUserDto.email() != null) user.setEmail(createUserDto.email());
        if (createUserDto.password() != null) user.setPassword(securityConfiguration.passwordEncoder().encode(createUserDto.password()));
        if (createUserDto.role() != null) user.setRoles(List.of(Role.builder().name(createUserDto.role()).build()));
        userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}