package com.accountbook.domain.entity;

import com.accountbook.domain.util.PaymentType;
import com.accountbook.domain.util.TransactionType;
import com.accountbook.presentation.dto.request.accountbook.InsertAccountBookRequestDto;
import com.accountbook.presentation.dto.request.accountbook.UpdateAccountBookRequestDto;
import com.accountbook.presentation.dto.response.accountbook.SelectAccountBookDetailResponseDto;
import com.accountbook.presentation.dto.response.accountbook.SelectAccountBookResponseDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "ACCOUNTBOOK")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class AccountBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accountbook_id")
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private String description;

    private String expenseCategory;
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private PaymentType paymentType;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    @JoinColumn(name = "is_delete")
    private Boolean isDelete;

    public void setUser(User user) {
        this.user = user;
    }

    public static AccountBook convertInsertAccountBookToAccountBookEntity(InsertAccountBookRequestDto insertAccountBookRequestDto, User user) {
        return AccountBook.builder()
                .transactionType(TransactionType.valueOf(insertAccountBookRequestDto.getTransactionType()))
                .amount(insertAccountBookRequestDto.getAmount())
                .description(insertAccountBookRequestDto.getDescription())
                .expenseCategory(insertAccountBookRequestDto.getExpenseCategory())
                .paymentType(PaymentType.valueOf(insertAccountBookRequestDto.getPaymentType()))
                .isDelete(false)
                .user(user)
                .build();
    }

    public static SelectAccountBookResponseDto convertAccountBookEntityToAccountBookList(AccountBook accountBook, String userName) {
        return SelectAccountBookResponseDto.builder()
                .id(accountBook.id)
                .transactionType(accountBook.transactionType.getName())
                .amount(accountBook.amount)
                .description(accountBook.description)
                .userName(userName)
                .build();
    }

    public static SelectAccountBookDetailResponseDto convertAccountBookEntityToAccountBookDetail(AccountBook accountBook, String userName) {
        return SelectAccountBookDetailResponseDto.builder()
                .id(accountBook.id)
                .transactionType(accountBook.transactionType.getName())
                .amount(accountBook.amount)
                .description(accountBook.description)
                .expenseCategory(accountBook.expenseCategory)
                .paymentType(accountBook.paymentType.name())
                .userName(userName)
                .build();
    }

    public void changeAccountBook(UpdateAccountBookRequestDto updateAccountBookRequestDto) {
        Long amount = updateAccountBookRequestDto.getAmount();
        String description = updateAccountBookRequestDto.getDescription();
        String expenseCategory = updateAccountBookRequestDto.getExpenseCategory();
        String paymentType = updateAccountBookRequestDto.getPaymentType();
        String transactionType = updateAccountBookRequestDto.getTransactionType();
        if (amount != null && this.amount != amount) this.amount = amount;
        if (!description.isEmpty()) this.description = description;
        if (!transactionType.isEmpty()) this.transactionType = TransactionType.valueOf(transactionType);
        if (!expenseCategory.isEmpty()) this.expenseCategory = expenseCategory;
        if (!paymentType.isEmpty()) this.paymentType = PaymentType.valueOf(paymentType);
    }

    public void changeAccountBook(boolean isDeleteFlag) {
        this.isDelete = isDeleteFlag;
    }
}

