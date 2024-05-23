package backend.newssseuk.domain.user.web.request;

import lombok.Getter;

@Getter
public class SignInDto {
    String email;

    String password;
}
