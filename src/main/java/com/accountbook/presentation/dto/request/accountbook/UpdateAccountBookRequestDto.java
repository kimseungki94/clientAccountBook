package com.accountbook.presentation.dto.request.accountbook;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAccountBookRequestDto {

    @NonNull
    Long accountBookId;

    private String transactionType;

    private Long amount;

    private String description;

    private String expenseCategory;

    private String paymentType;
}
