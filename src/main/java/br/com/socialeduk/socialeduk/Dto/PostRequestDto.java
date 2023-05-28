package br.com.socialeduk.socialeduk.Dto;

import lombok.Data;

@Data
public class PostRequestDto {
    private Long user_id;
    private String content;
}
