package com.accountbook.presentation.dto.response.accountbook;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SelectAccountBookResponseDto {
    private Long id;
    private String transactionType;
    private Long amount;
    private String description;
    private String userName;
}
