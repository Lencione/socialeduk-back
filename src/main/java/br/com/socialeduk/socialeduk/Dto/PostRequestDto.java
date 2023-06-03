package br.com.socialeduk.socialeduk.Dto;

import lombok.Data;

@Data
public class PostRequestDto {
    private Long userId;
    private String content;
}
