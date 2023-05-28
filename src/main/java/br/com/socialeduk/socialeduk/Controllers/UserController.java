package br.com.socialeduk.socialeduk.Controllers;

import br.com.socialeduk.socialeduk.Dto.Response;
import br.com.socialeduk.socialeduk.Entities.User;
import br.com.socialeduk.socialeduk.Services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody User user){
        try{
            return ResponseEntity.ok().body(new Response("success", "User registered successfull!", userService.registerUser(user)));
        }   catch (RuntimeException e){
            return ResponseEntity.badRequest().body(new Response("error", e.getMessage(), null));
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<Response> getAll(){
        return ResponseEntity.ok().body(new Response("success", "Users retrieved successfull!", userService.getAll()));
    }
}
