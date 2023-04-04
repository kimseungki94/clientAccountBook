package com.accountbook.domain.entity;

import com.accountbook.presentation.dto.request.user.JoinUserRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    public static User convertUserJoinToUserEntity(JoinUserRequestDto joinUserRequestDto) {
        return User.builder()
                .email(joinUserRequestDto.getEmail())
                .name(joinUserRequestDto.getName())
                .password(joinUserRequestDto.getPassword())
                .build();
    }
}
