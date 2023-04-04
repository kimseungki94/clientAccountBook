package com.accountbook.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserRefreshTokenResponseDto {
    private String message;
    private String accessToken;
}
