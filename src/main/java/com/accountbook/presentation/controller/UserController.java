package com.accountbook.presentation.controller;

import com.accountbook.application.UserService;
import com.accountbook.presentation.dto.request.UserJoinRequestDto;
import com.accountbook.presentation.dto.request.UserLoginRequestDto;
import com.accountbook.presentation.dto.response.CommonResponseDto;
import com.accountbook.presentation.dto.response.UserLoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public ResponseEntity<CommonResponseDto> joinUser(@Valid @RequestBody UserJoinRequestDto userJoinRequestDto) {
        CommonResponseDto commonResponseDto = userService.joinUser(userJoinRequestDto);
        URI uri = URI.create("/api/user/join/");

        return ResponseEntity.created(uri).body(commonResponseDto);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<UserLoginResponseDto> loginUser(@Valid @RequestBody UserLoginRequestDto userLoginRequestDto) {
        UserLoginResponseDto userLoginResponseDto = userService.loginUser(userLoginRequestDto);

        return ResponseEntity.ok(userLoginResponseDto);
    }
}
