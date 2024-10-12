package backend.newssseuk.domain.user.web.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignInDto {
    String email;

    String password;
}
