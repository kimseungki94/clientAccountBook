package com.accountbook.presentation.dto.request.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserRequestDto {
    @NonNull
    private String email;
    @NonNull
    private String password;
}
