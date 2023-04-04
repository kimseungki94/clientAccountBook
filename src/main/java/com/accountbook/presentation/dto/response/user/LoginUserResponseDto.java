package com.accountbook.presentation.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginUserResponseDto {
    private String message;
    private String accessToken;
    private String refreshToken;
}
