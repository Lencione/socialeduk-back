package br.com.socialeduk.socialeduk.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
