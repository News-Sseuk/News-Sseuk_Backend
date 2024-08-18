package backend.newssseuk.payload.status;

import backend.newssseuk.payload.BaseErrorCode;
import backend.newssseuk.payload.dto.ErrorReasonDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.function.Predicate;

@Getter
@RequiredArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON4000", "Bad request"),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "COMMON4001",  "Validation error"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON4002", "Requested resource not found"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON5000",  "Internal error"),
    DATA_ACCESS_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON5001",  "Data access error"),

    //USER_TOKEN Error
    INVALID_TOKEN(HttpStatus.BAD_REQUEST,"TOKEN4001", "Invalid token"),
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED,"TOKEN4002", "accessToken has expired"),
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED,"TOKEN4003", "refreshToken has expired"),
    TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND,"TOKEN4004", "Token not found"),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED,"TOKEN4005", "Unauthorized access, token is not valid for this operation"),
    UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED,"TOKEN4006", "Unsupported token access"),

    //user errors
    USER_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "USER4001", "User already exists"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"User4002","User not found"),
    SNS_LOGIN_WRONG_INFORMATION(HttpStatus.BAD_REQUEST, "SNS4003", "SNS information you entered is not valid."),

    //article errors
    ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND,"Article4002","Aricle not found"),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;


    public String getMessage(String message) {
        return Optional.ofNullable(message)
                .filter(Predicate.not(String::isBlank))
                .orElse(this.getMessage());
    }

    @Override
    public ErrorReasonDto getReason() {
        return ErrorReasonDto.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDto getReasonHttpStatus() {
        return ErrorReasonDto.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }
}
