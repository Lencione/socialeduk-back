package br.com.socialeduk.socialeduk.Controllers;

import br.com.socialeduk.socialeduk.Entities.User;
import br.com.socialeduk.socialeduk.Requests.LoginRequest;
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

import java.security.Key;
import java.util.Date;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/api/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest){
        User user = authenticateUser(loginRequest);

        if(user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }

        return ResponseEntity.ok(generateToken(loginRequest.getEmail()));
    }

    private User authenticateUser(LoginRequest loginRequest) {

        User user = userService.findByEmail(loginRequest.getEmail());
        if(user == null || !user.getPassword().equals(loginRequest.getPassword())){
            return null;
        }
        return user;
    }

    private String generateToken(String email){
        final long expirationTime = 86400000;
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key)
                .compact();
    }
}
