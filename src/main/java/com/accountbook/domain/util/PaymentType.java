package com.accountbook.domain.util;

import lombok.Getter;

@Getter
public enum PaymentType {
    CASH("CASH"),
    CARD("CARD"),
    ETC("ETC");

    private String name;

    PaymentType(String name) {
        this.name = name;
    }
}
