package backend.newssseuk.payload.exception;

import backend.newssseuk.payload.BaseErrorCode;
import backend.newssseuk.payload.dto.ErrorReasonDto;
import backend.newssseuk.payload.status.ErrorStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {

    private final BaseErrorCode code;

    public GeneralException(ErrorStatus errorStatus, String message) {
        super(errorStatus.getMessage(message));
        this.code = errorStatus;
    }

    public GeneralException(ErrorStatus errorStatus, String message, Throwable cause) {
        super(errorStatus.getMessage(message), cause);
        this.code = errorStatus;
    }

    public ErrorReasonDto getErrorReasonHttpStatus() {
        return this.code.getReasonHttpStatus();
    }
}
