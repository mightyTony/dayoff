package kr.tony.dayoff.user.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import kr.tony.dayoff.user.domain.User;
import kr.tony.dayoff.user.domain.UserRoleEnum;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.swing.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class SignUpRequest {

    @Email
    private final String email;

    @Pattern(regexp = "^[a-zA-Z0-9]{4,8}$", message = "닉네임은 4~8자의 영어와 숫자로 이루어져야 합니다.")
    private final String nickname;

    @NotNull(message = "비밀번호를 입력하세요.")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "비밀번호는 최소 8자 이상, 특수문자 1개 이상을 포함해야 합니다.")
    private final String password;

    @Pattern(regexp = "(?=.*[0-9])^[0-9]{11}$", message = "-을 제외한 10자리 번호를 입력해주세요")
    private final String phoneNumber;

    @Builder
    public SignUpRequest(String email, String nickname, String password, String phoneNumber) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    // dto -> entity
    public User toEntity(UserRoleEnum role, String encodedPassword) {
        return User.builder()
                .nickname(nickname)
                .email(email)
                .phoneNumber(phoneNumber)
                .password(encodedPassword)
                .role(role)
                .build();
    }
}
