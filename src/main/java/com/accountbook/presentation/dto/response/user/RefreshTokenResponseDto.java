package com.accountbook.presentation.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RefreshTokenResponseDto {
    private String message;
    private String accessToken;
}
