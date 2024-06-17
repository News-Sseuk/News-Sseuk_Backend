package backend.newssseuk.domain.user.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class JwtToken {
    private String grantType; // Bearer
    private String accessToken;
    private String refreshToken;
}
