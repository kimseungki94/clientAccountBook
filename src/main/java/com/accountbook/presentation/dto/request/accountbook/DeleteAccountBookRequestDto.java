package com.accountbook.presentation.dto.request.accountbook;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteAccountBookRequestDto {

    @NonNull
    Long accountBookId;
}
