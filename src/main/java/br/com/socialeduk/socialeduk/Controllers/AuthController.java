package br.com.socialeduk.socialeduk.Controllers;

import br.com.socialeduk.socialeduk.Dto.Response;
import br.com.socialeduk.socialeduk.Entities.User;
import br.com.socialeduk.socialeduk.Dto.LoginRequestDto;
import br.com.socialeduk.socialeduk.Helpers.EmailValidator;
import br.com.socialeduk.socialeduk.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequestDto loginRequest){

        try{
            User user = userService.authenticate(loginRequest);
            return ResponseEntity
                    .ok()
                    .body(new Response("success", "User authenticate successfull!", user));
        }catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new Response("error", e.getMessage(), null));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody User user){
        try{
            EmailValidator.validate(user.getEmail());
            return ResponseEntity.ok().body(new Response("success", "User registered successfull!", userService.registerUser(user)));
        }   catch (RuntimeException e){
            return ResponseEntity.badRequest().body(new Response("error", e.getMessage(), null));
        }
    }




}
