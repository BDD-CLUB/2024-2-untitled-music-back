package MusicPlatform.global.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}
