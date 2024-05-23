package backend.newssseuk.domain.user.web.request;

import lombok.Getter;

@Getter
public class SignUpDto {
    String name;

    String email;

    String password;
}
