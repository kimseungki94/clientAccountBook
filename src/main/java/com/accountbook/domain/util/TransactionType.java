package com.accountbook.domain.util;

import lombok.Getter;

@Getter
public enum TransactionType {
    INCOME("수입"),
    EXPANSE("지출");

    private String name;

    TransactionType(String name) {
        this.name = name;
    }
}
