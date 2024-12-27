package MusicPlatform.global.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import static MusicPlatform.global.error.ApplicationError.METHOD_ARGUMENT_NOT_VALID;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(BusinessException.class)
    public ErrorResponse businessExceptionHandler(BusinessException exception) {
        BusinessError businessError = exception.getBusinessError();
        log.warn("{} : {}", businessError.name(), businessError.getMessage(), exception);
        return ErrorResponse.builder(exception, businessError.getHttpStatus(), businessError.getMessage())
                .title(businessError.name())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException exception) {

        ApplicationException applicationException = new ApplicationException(METHOD_ARGUMENT_NOT_VALID);
        ApplicationError applicationError = applicationException.getApplicationError();

        log.warn("{} : {}", applicationError.name(), applicationError.getMessage(), exception);
        return ErrorResponse.builder(exception, applicationError.getHttpStatus(), applicationError.getMessage())
                .title(applicationError.name())
                .build();

    }
}
