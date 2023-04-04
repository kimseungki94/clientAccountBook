package com.accountbook.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ACCOUNTBOOK")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accountbook_id")
    private Long id;

    @Column(nullable = false)
    private String transactionType;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private String description;

    private String expenseCategory;

    private String paymentType;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

}

