package kr.tony.dayoff.config.security.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class ReissueTokenRequest {
    private final String refreshToken;

    public ReissueTokenRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}

