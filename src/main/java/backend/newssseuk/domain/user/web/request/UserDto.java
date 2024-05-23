package backend.newssseuk.domain.user.web.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {
    private String role;
    private String name;
    private String username;
}
