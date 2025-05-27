package br.ufrn.imd.cbm.controllers;

import br.ufrn.imd.cbm.annotations.AdminOnly;
import br.ufrn.imd.cbm.annotations.AnyAuthed;
import br.ufrn.imd.cbm.annotations.CustomerOnly;
import br.ufrn.imd.cbm.dtos.CreateUserDto;
import br.ufrn.imd.cbm.dtos.LoginUserDto;
import br.ufrn.imd.cbm.dtos.RecoveryJwtTokenDto;
import br.ufrn.imd.cbm.models.User;
import br.ufrn.imd.cbm.services.UserService;
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

    @PostMapping("/login")
    public ResponseEntity<RecoveryJwtTokenDto> authenticateUser(@RequestBody LoginUserDto loginUserDto) {
        RecoveryJwtTokenDto token = userService.authenticateUser(loginUserDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody CreateUserDto createUserDto) {
        userService.createUser(createUserDto);
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
        User user = userService.findUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @AdminOnly
    @GetMapping("/find-by-email")
    public ResponseEntity<User> findUserByEmail(@RequestParam String email){
        User user = userService.findUserByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @AnyAuthed
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUserById(@PathVariable Long id, @RequestBody CreateUserDto user){
        userService.updateUserById(id,user);
        return new ResponseEntity<>("User atualizado com sucesso", HttpStatus.OK);
    }

    @AnyAuthed
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id){
        userService.deleteUserById(id);
        return new ResponseEntity<>("User deletado com sucesso", HttpStatus.NO_CONTENT);
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
        userService.deleteUserById(user.getId());
        return new ResponseEntity<>("User deletado com sucesso", HttpStatus.NO_CONTENT);
    }
}
