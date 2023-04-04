package com.accountbook.presentation.dto.response.accountbook;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SelectAccountBookDetailResponseDto {
    private Long id;
    private String transactionType;
    private Long amount;
    private String description;
    private String expenseCategory;
    private String paymentType;
    private String userName;
}
