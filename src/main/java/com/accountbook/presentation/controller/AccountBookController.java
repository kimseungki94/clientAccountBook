package com.accountbook.presentation.controller;


import com.accountbook.application.AccountBookService;
import com.accountbook.presentation.dto.request.accountbook.DeleteAccountBookRequestDto;
import com.accountbook.presentation.dto.request.accountbook.InsertAccountBookRequestDto;
import com.accountbook.presentation.dto.request.accountbook.UpdateAccountBookRequestDto;
import com.accountbook.presentation.dto.response.accountbook.SelectAccountBookDetailResponseDto;
import com.accountbook.presentation.dto.response.accountbook.SelectAccountBookResponseDto;
import com.accountbook.presentation.dto.response.CommonResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/account-book")
@RequiredArgsConstructor
public class AccountBookController {
    private final AccountBookService accountBookService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<CommonResponseDto> insertAccountBook(@Valid @RequestBody InsertAccountBookRequestDto insertAccountBookRequestDto, Authentication authentication) {
        String email = authentication.getName();
        CommonResponseDto userLoginResponseDto = accountBookService.insertAccountBook(insertAccountBookRequestDto, email);
        URI uri = URI.create("/api/account-book/");

        return ResponseEntity.created(uri).body(userLoginResponseDto);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<SelectAccountBookResponseDto>> findAccountBooks(Authentication authentication) {
        String email = authentication.getName();
        List<SelectAccountBookResponseDto> selectAccountBookResponseDtoList = accountBookService.findAccountBooks(email);

        return ResponseEntity.ok(selectAccountBookResponseDtoList);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResponseEntity<List<SelectAccountBookResponseDto>> findDeleteAccountBooks(Authentication authentication) {
        String email = authentication.getName();
        List<SelectAccountBookResponseDto> selectAccountBookResponseDtoList = accountBookService.findDeleteAccountBooks(email);

        return ResponseEntity.ok(selectAccountBookResponseDtoList);
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ResponseEntity<SelectAccountBookDetailResponseDto> findAccountBookDetail(@RequestParam("id") String id, Authentication authentication) {
        String email = authentication.getName();
        SelectAccountBookDetailResponseDto selectAccountBookDetailResponseDto = accountBookService.findAccountBookDetail(id, email);

        return ResponseEntity.ok(selectAccountBookDetailResponseDto);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity<CommonResponseDto> updateAccountBook(@Valid @RequestBody UpdateAccountBookRequestDto updateAccountBookRequestDto,
                                                               Authentication authentication) {
        String email = authentication.getName();
        CommonResponseDto commonResponseDto = accountBookService.updateAccountBook(updateAccountBookRequestDto, email);

        return ResponseEntity.ok(commonResponseDto);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.PUT)
    public ResponseEntity<CommonResponseDto> deleteAccountBook(@Valid @RequestBody DeleteAccountBookRequestDto deleteAccountBookRequestDto,
                                                               Authentication authentication) {
        String email = authentication.getName();
        CommonResponseDto commonResponseDto = accountBookService.deleteAccountBook(deleteAccountBookRequestDto, email);

        return ResponseEntity.ok(commonResponseDto);
    }

    @RequestMapping(value = "/restore", method = RequestMethod.PUT)
    public ResponseEntity<CommonResponseDto> restoreAccountBook(@Valid @RequestBody DeleteAccountBookRequestDto deleteAccountBookRequestDto,
                                                                Authentication authentication) {
        String email = authentication.getName();
        CommonResponseDto commonResponseDto = accountBookService.restoreAccountBook(deleteAccountBookRequestDto, email);

        return ResponseEntity.ok(commonResponseDto);
    }
}
