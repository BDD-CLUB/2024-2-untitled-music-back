package MusicPlatform.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum BusinessError {
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
