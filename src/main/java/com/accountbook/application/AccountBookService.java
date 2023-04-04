package com.accountbook.application;

import com.accountbook.domain.entity.AccountBook;
import com.accountbook.domain.entity.User;
import com.accountbook.infrastrcture.AccountBookRepository;
import com.accountbook.infrastrcture.UserRepository;
import com.accountbook.presentation.dto.request.accountbook.DeleteAccountBookRequestDto;
import com.accountbook.presentation.dto.request.accountbook.InsertAccountBookRequestDto;
import com.accountbook.presentation.dto.request.accountbook.UpdateAccountBookRequestDto;
import com.accountbook.presentation.dto.response.accountbook.SelectAccountBookDetailResponseDto;
import com.accountbook.presentation.dto.response.accountbook.SelectAccountBookResponseDto;
import com.accountbook.presentation.dto.response.CommonResponseDto;
import com.accountbook.presentation.exception.NoAccountBookDataException;
import com.accountbook.presentation.exception.NoAuthentication;
import com.accountbook.presentation.exception.WrongEmailOrPasswordException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountBookService {
    private final AccountBookRepository accountBookRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommonResponseDto insertAccountBook(InsertAccountBookRequestDto insertAccountBookRequestDto, String email) {
        User user = findUserByEmail(email);
        AccountBook accountBook = AccountBook.convertInsertAccountBookToAccountBookEntity(insertAccountBookRequestDto, user);
        accountBookRepository.save(accountBook);

        return CommonResponseDto
                .builder()
                .responseMessage("success")
                .responseDescription("가계부 등록 성공")
                .build();
    }

    public List<SelectAccountBookResponseDto> findAccountBooks(String email) {
        User user = findUserByEmail(email);
        Long userId = user.getId();
        String userName = user.getName();
        List<AccountBook> accountBookList = accountBookRepository.findByUserIdAndIsDelete(userId, false);

        return accountBookList.stream()
                .map(accountBook -> AccountBook.convertAccountBookEntityToAccountBookList(accountBook, userName))
                .toList();
    }

    public List<SelectAccountBookResponseDto> findDeleteAccountBooks(String email) {
        User user = findUserByEmail(email);
        Long userId = user.getId();
        String userName = user.getName();
        List<AccountBook> accountBookList = accountBookRepository.findByUserIdAndIsDelete(userId, true);

        return accountBookList.stream()
                .map(accountBook -> AccountBook.convertAccountBookEntityToAccountBookList(accountBook, userName))
                .toList();
    }

    public SelectAccountBookDetailResponseDto findAccountBookDetail(String id, String email) {
        User user = findUserByEmail(email);
        String userName = user.getName();
        AccountBook accountBook = accountBookRepository.findById(Long.parseLong(id)).orElseThrow(() -> new NoAccountBookDataException());

        return AccountBook.convertAccountBookEntityToAccountBookDetail(accountBook, userName);
    }

    @Transactional
    public CommonResponseDto updateAccountBook(UpdateAccountBookRequestDto updateAccountBookRequestDto, String email) {
        User user = findUserByEmail(email);
        Long accountBookId = updateAccountBookRequestDto.getAccountBookId();
        Long userId = user.getId();
        AccountBook accountBook = accountBookRepository.findByIdAndIsDelete(accountBookId, false)
                .orElseThrow(() -> new NoAccountBookDataException());

        if (!accountBook.getUser().getId().equals(userId)) throw new NoAuthentication();
        accountBook.changeAccountBook(updateAccountBookRequestDto);
        return CommonResponseDto
                .builder()
                .responseMessage("success")
                .responseDescription("가계부 업데이트 성공")
                .build();
    }

    @Transactional
    public CommonResponseDto deleteAccountBook(DeleteAccountBookRequestDto deleteAccountBookRequestDto, String email) {
        User user = findUserByEmail(email);
        Long accountBookId = deleteAccountBookRequestDto.getAccountBookId();
        Long userId = user.getId();
        AccountBook accountBook = accountBookRepository.findByIdAndIsDelete(accountBookId, false)
                .orElseThrow(() -> new NoAccountBookDataException());

        if (!accountBook.getUser().getId().equals(userId)) throw new NoAuthentication();
        accountBook.changeAccountBook(true);

        return CommonResponseDto
                .builder()
                .responseMessage("success")
                .responseDescription("가계부 삭제 성공")
                .build();
    }

    @Transactional
    public CommonResponseDto restoreAccountBook(DeleteAccountBookRequestDto deleteAccountBookRequestDto, String email) {
        User user = findUserByEmail(email);
        Long accountBookId = deleteAccountBookRequestDto.getAccountBookId();
        Long userId = user.getId();
        AccountBook accountBook = accountBookRepository.findByIdAndIsDelete(accountBookId, true)
                .orElseThrow(() -> new NoAccountBookDataException());

        if (!accountBook.getUser().getId().equals(userId)) throw new NoAuthentication();
        accountBook.changeAccountBook(false);

        return CommonResponseDto
                .builder()
                .responseMessage("success")
                .responseDescription("가계부 복구 성공")
                .build();
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new WrongEmailOrPasswordException());
    }
}
