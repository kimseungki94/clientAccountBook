package com.accountbook.domain.entity;

import com.accountbook.presentation.dto.request.UserJoinRequestDto;
import com.accountbook.presentation.dto.request.UserLoginRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name = "USER")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    public static User convertUserJoinToUserEntity(UserJoinRequestDto userJoinRequestDto) {
        return User.builder()
                .email(userJoinRequestDto.getEmail())
                .name(userJoinRequestDto.getName())
                .password(userJoinRequestDto.getPassword())
                .build();
    }

    public static User convertUserLoginToUserEntity(UserLoginRequestDto userLoginRequestDto) {
        return null;
    }
}
