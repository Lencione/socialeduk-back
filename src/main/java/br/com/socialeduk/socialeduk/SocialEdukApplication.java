package br.com.socialeduk.socialeduk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class SocialEdukApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocialEdukApplication.class, args);
    }

    @Autowired

    @GetMapping("/teste")
    public String teste(){
        return "Sua mãe";
    }



}
