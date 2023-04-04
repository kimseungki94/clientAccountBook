package com.accountbook.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserJoinRequestDto {
    @NonNull
    private String email;
    @NonNull
    private String password;

    public void encodingPassword(final String password) {
        this.password = password;
    }
}
