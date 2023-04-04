package com.accountbook.presentation.dto.request.accountbook;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InsertAccountBookRequestDto {
    @NonNull
    private String transactionType;
    @NonNull
    private Long amount;
    @NonNull
    private String description;

    private String expenseCategory;

    private String paymentType;
}
