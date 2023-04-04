package com.accountbook.application;

import com.accountbook.domain.entity.User;
import com.accountbook.infrastrcture.UserRepository;
import com.accountbook.presentation.dto.request.user.JoinUserRequestDto;
import com.accountbook.presentation.dto.request.user.LoginUserRequestDto;
import com.accountbook.presentation.dto.response.CommonResponseDto;
import com.accountbook.presentation.dto.response.user.LoginUserResponseDto;
import com.accountbook.presentation.dto.response.user.RefreshTokenResponseDto;
import com.accountbook.presentation.exception.ExpireTokenException;
import com.accountbook.presentation.exception.SameEmailException;
import com.accountbook.presentation.exception.WrongEmailOrPasswordException;
import com.accountbook.presentation.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    @Value("${springboot.jwt.secret}")
    private String secretKey = "secretKey";

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder encoder;
    private final RedisTemplate<String, String> redisTemplate;

    @Transactional
    public CommonResponseDto joinUser(final JoinUserRequestDto joinUserRequestDto) {
        String email = joinUserRequestDto.getEmail();
        String password = encoder.encode(joinUserRequestDto.getPassword());
        joinUserRequestDto.encodingPassword(password);

        if (existsByEmail(email)) throw new SameEmailException();
        User user = User.convertUserJoinToUserEntity(joinUserRequestDto);
        userRepository.save(user);

        return CommonResponseDto.builder()
                .responseMessage("success")
                .responseDescription("회원가입 성공")
                .build();
    }

    @Transactional
    public LoginUserResponseDto loginUser(final LoginUserRequestDto loginUserRequestDto) {
        String email = loginUserRequestDto.getEmail();
        String password = loginUserRequestDto.getPassword();

        User user = findUserByEmail(email);
        if (!encoder.matches(password, user.getPassword())) throw new WrongEmailOrPasswordException();

        String refreshToken = jwtUtil.createRefreshToken(email);
        setRedisRefreshToken(email, refreshToken);

        return LoginUserResponseDto.builder()
                .message("success")
                .accessToken(jwtUtil.createAccessToken(email))
                .refreshToken(refreshToken)
                .build();
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new WrongEmailOrPasswordException());
    }

    private boolean existsByEmail(final String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional
    public RefreshTokenResponseDto checkRefreshToken(String refreshToken) {
        String token = refreshToken.split(" ")[1];
        boolean validateToken = jwtUtil.validateToken(token, secretKey);
        if (!validateToken) {
            throw new ExpireTokenException();
        }
        String loginId = JwtUtil.getEmail(token, secretKey);
        String redisRefreshToken = getRedisRefreshToken(loginId);

        if (!token.equals(redisRefreshToken)) throw new ExpireTokenException();

        String accessToken = jwtUtil.createAccessToken(loginId);
        return RefreshTokenResponseDto
                .builder()
                .accessToken(accessToken)
                .message("success")
                .build();
    }

    public CommonResponseDto userLogout(String email) {
        deleteRedisRefreshToken(email);
        return CommonResponseDto
                .builder()
                .responseMessage("success")
                .responseDescription("로그아웃 성공")
                .build();
    }

    private String setRedisRefreshToken(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
        return value;
    }

    private String getRedisRefreshToken(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    private void deleteRedisRefreshToken(String key) {
        redisTemplate.delete(key);
    }
}
