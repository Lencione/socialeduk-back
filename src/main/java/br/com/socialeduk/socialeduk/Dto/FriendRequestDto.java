package br.com.socialeduk.socialeduk.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FriendRequestDto {
    public Long sender;
    public Long receiver;
}
