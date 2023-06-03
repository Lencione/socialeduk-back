package br.com.socialeduk.socialeduk.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BlockAndSendFriendRequestDto {
    private Long sender;
    private Long receiver;
}
