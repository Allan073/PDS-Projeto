package br.ufrn.imd.framework.services;

import br.ufrn.imd.framework.configs.SecurityConfiguration;
import br.ufrn.imd.framework.dtos.CreateUserDto;
import br.ufrn.imd.framework.dtos.LoginUserDto;
import br.ufrn.imd.framework.dtos.RecoveryJwtTokenDto;
import br.ufrn.imd.framework.exceptions.NotFoundException;
import br.ufrn.imd.framework.models.Role;
import br.ufrn.imd.framework.models.User;
import br.ufrn.imd.framework.repositories.UserRepository;
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
    protected SecurityConfiguration securityConfiguration;

    // Método responsável por autenticar um usuário e retornar um token JWT
    public RecoveryJwtTokenDto authenticateUser(LoginUserDto loginUserDto) {
        // Cria um objeto de autenticação com o email e a senha do usuário
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());

        // Autentica o usuário com as credenciais fornecidas
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // Obtém o objeto UserDetails do usuário autenticado
        User user = (User) authentication.getPrincipal();

        // Gera um token JWT para o usuário autenticado
        return new RecoveryJwtTokenDto(jwtTokenService.generateToken(user));
    }

    public User createUser(CreateUserDto createUserDto) {

        User newUser = User.builder()
                .name(createUserDto.name())
                .email(createUserDto.email())
                .password(securityConfiguration.passwordEncoder().encode(createUserDto.password()))
                .roles(List.of(Role.builder().name(createUserDto.role()).build()))
                .orders(new ArrayList<>())
                .addresses(new ArrayList<>())
                .build();

        userRepository.save(newUser);
        return newUser;
    }

    public User findUserById(Long id) throws NotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
    }

    public User findUserByEmail(String email) throws NotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Email não encontrado"));
    }

    public void updateUserById(Long id, CreateUserDto createUserDto) throws NotFoundException {
        User user = null;
        try {
            user = findUserById(id);
        } catch (NotFoundException e) {
            throw e;
        }
        if (createUserDto.name() != null) user.setName(createUserDto.name());
        if (createUserDto.email() != null) user.setEmail(createUserDto.email());
        if (createUserDto.password() != null) user.setPassword(securityConfiguration.passwordEncoder().encode(createUserDto.password()));
        if (createUserDto.role() != null) user.setRoles(List.of(Role.builder().name(createUserDto.role()).build()));
        userRepository.save(user);
    }

    public void deleteUserById(Long id) throws NotFoundException {
        try {
            userRepository.delete(findUserById(id));
        } catch (NotFoundException e) {
            throw e;
        }
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}