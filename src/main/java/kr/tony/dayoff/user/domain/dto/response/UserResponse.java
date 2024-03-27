package kr.tony.dayoff.user.domain.dto.response;

import kr.tony.dayoff.user.domain.User;
import kr.tony.dayoff.user.domain.UserRoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
public class UserResponse {

    private final String email;
    private final String nickname;
    private final String phoneNumber;
    private final UserRoleEnum role;
    private final String profileImage;


    private UserResponse(User user) {
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.phoneNumber = user.getPhoneNumber();
        this.role = user.getRole();
        this.profileImage = user.getProfileImage();
    }

    // User Entity -> response dto
    public static UserResponse of(User user) {
        return new UserResponse(user);
    }
}
