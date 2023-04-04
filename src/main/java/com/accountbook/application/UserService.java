package com.accountbook.application;

import com.accountbook.domain.entity.User;
import com.accountbook.infrastrcture.UserRepository;
import com.accountbook.presentation.dto.request.UserJoinRequestDto;
import com.accountbook.presentation.dto.request.UserLoginRequestDto;
import com.accountbook.presentation.dto.response.CommonResponseDto;
import com.accountbook.presentation.dto.response.UserLoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public CommonResponseDto joinUser(final UserJoinRequestDto userJoinRequestDto) {
        String email = userJoinRequestDto.getEmail();
        if (existsByEmail(email)) throw new RuntimeException();
        User user = User.convertUserJoinToUserEntity(userJoinRequestDto);
        userRepository.save(user);

        return CommonResponseDto.builder()
                .responseMessage("success")
                .responseDescription("회원가입 성공")
                .build();
    }

    @Transactional
    public UserLoginResponseDto loginUser(final UserLoginRequestDto userLoginRequestDto) {
        String email = userLoginRequestDto.getEmail();
        String password = userLoginRequestDto.getPassword();

        if (existsByEmail(email)) throw new RuntimeException();
        userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException());
        User user = User.convertUserLoginToUserEntity(userLoginRequestDto);
        userRepository.save(user);

        return UserLoginResponseDto.builder()
                .message("success")
                .accessToken("success")
                .refreshToken("회원가입 성공")
                .build();
    }

    private boolean existsByEmail(final String email) {
        return userRepository.existsByEmail(email);
    }
}
