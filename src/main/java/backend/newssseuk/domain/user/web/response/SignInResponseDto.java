package backend.newssseuk.domain.user.web.response;

import backend.newssseuk.domain.user.jwt.JwtToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignInResponseDto {
    JwtToken token;
}
