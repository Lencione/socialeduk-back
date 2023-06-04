package br.com.socialeduk.socialeduk.Helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    public static boolean validate(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);

        if(!matcher.matches()){
            throw new IllegalArgumentException("Email inválido");
        }
        return matcher.matches();
    }
}
