package br.com.socialeduk.socialeduk.Controllers;

import br.com.socialeduk.socialeduk.Entities.User;
import br.com.socialeduk.socialeduk.Services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user){
        try{
            User registeredUser = userService.registerUser(user);
            return ResponseEntity.ok("Cadastrado com sucesso!");
        }   catch (RuntimeException e){
            String errorMessage = e.getMessage();
            return ResponseEntity.badRequest().body(errorMessage);
        }
    }
}
