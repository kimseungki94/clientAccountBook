package com.accountbook.application;

import com.accountbook.domain.entity.User;
import com.accountbook.infrastrcture.UserRepository;
import com.accountbook.presentation.dto.request.user.JoinUserRequestDto;
import com.accountbook.presentation.dto.request.user.LoginUserRequestDto;
import com.accountbook.presentation.dto.response.CommonResponseDto;
import com.accountbook.presentation.exception.WrongEmailOrPasswordException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BCryptPasswordEncoder encoder;

    @Test
    @DisplayName("회원가입 시 회원정보, 키워드가 테이블에 저장되어 회원가입 성공")
    void 회원가입_정상처리() {
        JoinUserRequestDto joinUserRequestDto = new JoinUserRequestDto("test@test.com", "test1234!","김승기");
        CommonResponseDto commonResponseDto = new CommonResponseDto("success","회원가입 성공");

        when(userRepository.existsByEmail(any())).thenReturn(false);

        CommonResponseDto responseDto = userService.joinUser(joinUserRequestDto);
        assertEquals(commonResponseDto.getResponseMessage(), responseDto.getResponseMessage());
        assertEquals(commonResponseDto.getResponseDescription(), responseDto.getResponseDescription());
    }

    @Test
    @DisplayName("유저 테이블에 존재하지 않은 아이디 패스워드를 입력하는 경우 WrongEmailOrPasswordException 던지며 로그인 실패")
    void 로그인_회원정보불일치() {
        String email = "test@test.com";
        String password = "test1234!";
        LoginUserRequestDto loginUserRequestDto = new LoginUserRequestDto(email, password);

        when(userRepository.findByEmail(any())).thenThrow(new WrongEmailOrPasswordException());

        assertThrows(WrongEmailOrPasswordException.class, () -> userService.loginUser(loginUserRequestDto));
    }
}
