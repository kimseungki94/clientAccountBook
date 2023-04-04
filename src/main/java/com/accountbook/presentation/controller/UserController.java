package com.accountbook.presentation.controller;

import com.accountbook.application.UserService;
import com.accountbook.presentation.dto.request.user.JoinUserRequestDto;
import com.accountbook.presentation.dto.request.user.LoginUserRequestDto;
import com.accountbook.presentation.dto.response.CommonResponseDto;
import com.accountbook.presentation.dto.response.user.LoginUserResponseDto;
import com.accountbook.presentation.dto.response.user.RefreshTokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public ResponseEntity<CommonResponseDto> joinUser(@Valid @RequestBody JoinUserRequestDto joinUserRequestDto) {
        CommonResponseDto commonResponseDto = userService.joinUser(joinUserRequestDto);
        URI uri = URI.create("/api/user/join/");

        return ResponseEntity.created(uri).body(commonResponseDto);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<LoginUserResponseDto> loginUser(@Valid @RequestBody LoginUserRequestDto loginUserRequestDto) {
        LoginUserResponseDto loginUserResponseDto = userService.loginUser(loginUserRequestDto);

        return ResponseEntity.ok(loginUserResponseDto);
    }

    @RequestMapping(value = "/refresh-token", method = RequestMethod.GET)
    public ResponseEntity<RefreshTokenResponseDto> checkRefreshToken(HttpServletRequest request) {
        String refreshToken = request.getHeader("REFRESH-TOKEN");
        RefreshTokenResponseDto userCheckRefreshTokenResponseDto = userService.checkRefreshToken(refreshToken);
        return ResponseEntity.ok(userCheckRefreshTokenResponseDto);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseEntity<CommonResponseDto> logoutUser(Authentication authentication) {
        String email = authentication.getName();
        CommonResponseDto commonResponseDto = userService.userLogout(email);
        return ResponseEntity.ok(commonResponseDto);
    }
}
