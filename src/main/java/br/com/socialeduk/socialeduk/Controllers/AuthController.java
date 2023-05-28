package br.com.socialeduk.socialeduk.Controllers;

import br.com.socialeduk.socialeduk.Dto.Response;
import br.com.socialeduk.socialeduk.Entities.User;
import br.com.socialeduk.socialeduk.Dto.LoginRequest;
import br.com.socialeduk.socialeduk.Services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.spec.OAEPParameterSpec;
import java.security.Key;
import java.util.Date;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/api/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest){

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




}
