package br.ufrn.imd.sbm.controllers;

import br.ufrn.imd.framework.annotations.AdminOnly;
import br.ufrn.imd.framework.annotations.AnyAuthed;
import br.ufrn.imd.framework.annotations.CustomerOnly;
import br.ufrn.imd.framework.dtos.CreateUserDto;
import br.ufrn.imd.framework.dtos.LoginUserDto;
import br.ufrn.imd.framework.dtos.RecoveryJwtTokenDto;
import br.ufrn.imd.framework.exceptions.NotFoundException;
import br.ufrn.imd.framework.models.User;
import br.ufrn.imd.framework.services.UserService;
import br.ufrn.imd.sbm.services.UserSubscriptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserSubscriptionsService userSubscriptionsService;
    @PostMapping("/login")
    public ResponseEntity<RecoveryJwtTokenDto> authenticateUser(@RequestBody LoginUserDto loginUserDto) {
        RecoveryJwtTokenDto token = userService.authenticateUser(loginUserDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody CreateUserDto createUserDto) {
        userSubscriptionsService.createUserSubscriptions(userService.createUser(createUserDto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @AnyAuthed
    @GetMapping("/test")
    public ResponseEntity<String> getAuthenticationTest() {
        return new ResponseEntity<>("Autenticado com sucesso", HttpStatus.OK);
    }

    @CustomerOnly
    @GetMapping("/test/customer")
    public ResponseEntity<String> getCustomerAuthenticationTest() {
        return new ResponseEntity<>("Cliente autenticado com sucesso", HttpStatus.OK);
    }

    @AdminOnly
    @GetMapping("/test/administrator")
    public ResponseEntity<String> getAdminAuthenticationTest() {
        return new ResponseEntity<>("Administrador autenticado com sucesso", HttpStatus.OK);
    }

    @AdminOnly
    @GetMapping("/test/getuser")
    public ResponseEntity<String> getUserAuthenticationTest(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.OK).body(user.getEmail());
    }

    @AdminOnly
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        try {
            User user = userService.findUserById(id);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @AdminOnly
    @GetMapping("/find-by-email")
    public ResponseEntity<User> findUserByEmail(@RequestParam String email){
        try {
            User user = userService.findUserByEmail(email);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @AnyAuthed
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUserById(@PathVariable Long id, @RequestBody CreateUserDto user){
        try {
            userService.updateUserById(id,user);
            return new ResponseEntity<>("User atualizado com sucesso", HttpStatus.OK);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @AnyAuthed
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id){
        try {
            userSubscriptionsService.deleteUserSubscriptions(id);
            userService.deleteUserById(id);
            return new ResponseEntity<>("User deletado com sucesso", HttpStatus.NO_CONTENT);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @AdminOnly
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @AnyAuthed
    @GetMapping("/self")
    public ResponseEntity<User> getSelfUser(@AuthenticationPrincipal User user){
        System.out.println(user);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @AnyAuthed
    @DeleteMapping("/self")
    public ResponseEntity<String> deleteSelfUser(@AuthenticationPrincipal User user){
        try {
            userSubscriptionsService.deleteUserSubscriptions(user.getId());
            userService.deleteUserById(user.getId());
            return new ResponseEntity<>("User deletado com sucesso", HttpStatus.NO_CONTENT);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Se você está vendo este erro algo catastrófico " +
                    "aconteceu, e sua conta não foi encontrada no repositório.");
        }
    }
}
