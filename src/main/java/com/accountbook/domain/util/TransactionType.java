package com.accountbook.domain.util;

import lombok.Getter;

@Getter
public enum TransactionType {
    INCOME("INCOME"),
    EXPANSE("EXPANSE"),
    ETC("ETC");

    private String name;

    TransactionType(String name) {
        this.name = name;
    }
}
