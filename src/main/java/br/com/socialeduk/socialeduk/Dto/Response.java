package br.com.socialeduk.socialeduk.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {
    public String status;
    public String message;
    public Object data;
}
