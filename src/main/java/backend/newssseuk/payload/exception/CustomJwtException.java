package backend.newssseuk.payload.exception;

import backend.newssseuk.payload.status.ErrorStatus;
import io.jsonwebtoken.Claims;
import lombok.Getter;

@Getter
public class CustomJwtException extends RuntimeException {
    private final ErrorStatus errorStatus;
    private final Claims claims;

    public CustomJwtException(ErrorStatus errorStatus, Claims claims) {
        super(errorStatus.getMessage());
        this.errorStatus = errorStatus;
        this.claims = claims;
    }

}
