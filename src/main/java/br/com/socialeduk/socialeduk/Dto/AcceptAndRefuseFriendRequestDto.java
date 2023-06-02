package br.com.socialeduk.socialeduk.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AcceptAndRefuseFriendRequestDto {
    private Long userId;
    private Long friendRequestId;
}
