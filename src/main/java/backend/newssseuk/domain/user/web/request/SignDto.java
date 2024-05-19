package backend.newssseuk.domain.user.web.request;

import lombok.Getter;

public class SignDto {

    @Getter
    public static class SignUpDto{

        String name;

        String email;

        String password;
    }

    @Getter
    public static class SignInDto {
        String email;

        String password;
    }
}

