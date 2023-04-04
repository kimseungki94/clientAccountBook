package com.accountbook.domain.util;

import lombok.Getter;

@Getter
public enum PaymentType {
    CASH("현금"),
    CARD("카드");

    private String name;

    PaymentType(String name) {
        this.name = name;
    }
}
