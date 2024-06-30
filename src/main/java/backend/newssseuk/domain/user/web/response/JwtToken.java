package backend.newssseuk.domain.user.web.response;

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
