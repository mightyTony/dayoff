package kr.tony.dayoff.config.security.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

// TODO : force
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class TokenResponse {

    private final String accessToken;
    private final String refreshToken;

    public TokenResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
