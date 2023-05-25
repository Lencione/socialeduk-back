package br.com.socialeduk.socialeduk.Controllers;

import br.com.socialeduk.socialeduk.Entities.User;
import br.com.socialeduk.socialeduk.Services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            userService.registerUser(user);
            return ResponseEntity.ok("Cadastrado com sucesso!");
        }   catch (RuntimeException e){
            String errorMessage = e.getMessage();
            return ResponseEntity.badRequest().body(errorMessage);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id){
        try{
            User user = userService.findById(id);
            return ResponseEntity.ok(user);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }
}
