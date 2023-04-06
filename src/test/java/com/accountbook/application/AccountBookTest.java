package com.accountbook.application;

import com.accountbook.domain.entity.AccountBook;
import com.accountbook.domain.entity.User;
import com.accountbook.domain.util.PaymentType;
import com.accountbook.domain.util.TransactionType;
import com.accountbook.infrastrcture.AccountBookRepository;
import com.accountbook.infrastrcture.UserRepository;
import com.accountbook.presentation.dto.request.accountbook.InsertAccountBookRequestDto;
import com.accountbook.presentation.dto.request.user.JoinUserRequestDto;
import com.accountbook.presentation.dto.response.CommonResponseDto;
import com.accountbook.presentation.dto.response.accountbook.SelectAccountBookResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountBookTest {
    @InjectMocks
    private AccountBookService accountBookService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AccountBookRepository accountBookRepository;

    AccountBook accountBook;
    List<AccountBook> accountBookList;
    User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .email("test@test.com")
                .password("test1234!")
                .name("김승기")
                .build();

        accountBook = AccountBook.builder()
                .id(1L)
                .transactionType(TransactionType.EXPANSE)
                .amount(70000L)
                .description("정기결제 교육비 지출")
                .expenseCategory("교육비")
                .paymentType(PaymentType.CARD)
                .isDelete(false)
                .user(user)
                .build();

        accountBookList = new ArrayList<>();
        accountBookList.add(accountBook);
    }

    @Test
    @DisplayName("가계부 등록 시 체크")
    void 가계부등록_정상처리() {
        InsertAccountBookRequestDto insertAccountBookRequestDto = new InsertAccountBookRequestDto("EXPANSE", 70000L, "정기결제 교육비 지출"
                , "교육비", "CARD");

        CommonResponseDto commonResponseDto = new CommonResponseDto("success", "가계부 등록 성공");

        when(userRepository.findByEmail(any())).thenReturn(Optional.ofNullable(user));
        CommonResponseDto responseDto = accountBookService.insertAccountBook(insertAccountBookRequestDto, user.getEmail());
        assertEquals(commonResponseDto.getResponseMessage(), responseDto.getResponseMessage());
        assertEquals(commonResponseDto.getResponseDescription(), responseDto.getResponseDescription());
    }

    @Test
    @DisplayName("가계부 조회 시 체크")
    void 가계부조회_정상처리() {
        String email = user.getEmail();

        when(userRepository.findByEmail(any())).thenReturn(Optional.ofNullable(user));
        when(accountBookRepository.findByUserIdAndIsDelete(any(), anyBoolean())).thenReturn(accountBookList);
        List<SelectAccountBookResponseDto> accountBooks = accountBookService.findAccountBooks(email);

        assertEquals(accountBookList.get(0).getTransactionType().getName(), accountBooks.get(0).getTransactionType());
        assertEquals(accountBookList.get(0).getAmount(), accountBooks.get(0).getAmount());
        assertEquals(accountBookList.get(0).getDescription(), accountBooks.get(0).getDescription());
    }
}
