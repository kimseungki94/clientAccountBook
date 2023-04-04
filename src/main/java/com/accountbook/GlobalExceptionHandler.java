package com.accountbook;

import com.accountbook.presentation.exception.CommonExceptionResponse;
import com.accountbook.presentation.exception.ExpireTokenException;
import com.accountbook.presentation.exception.SameEmailException;
import com.accountbook.presentation.exception.WrongEmailOrPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(WrongEmailOrPasswordException.class)
    public ResponseEntity<CommonExceptionResponse> wrongEmailOrPasswordException() {
        return new ResponseEntity<CommonExceptionResponse>(
                CommonExceptionResponse
                        .builder()
                        .exceptionMessage("user exception")
                        .exceptionDetail("아이디 혹은 패스워드를 확인부탁드립니다")
                        .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SameEmailException.class)
    public ResponseEntity<CommonExceptionResponse> sameEmailException() {
        return new ResponseEntity<CommonExceptionResponse>(
                CommonExceptionResponse
                        .builder()
                        .exceptionMessage("user exception")
                        .exceptionDetail("이미 존재하는 아이디입니다.")
                        .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExpireTokenException.class)
    public ResponseEntity<CommonExceptionResponse> expireTokenException() {
        return new ResponseEntity<CommonExceptionResponse>(
                CommonExceptionResponse
                        .builder()
                        .exceptionMessage("user exception")
                        .exceptionDetail("토큰이 만료되었습니다.")
                        .build(), HttpStatus.BAD_REQUEST);
    }
}
