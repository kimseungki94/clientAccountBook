package com.accountbook.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequestDto {
    @NonNull
    private String email;
    @NonNull
    private String password;
}
