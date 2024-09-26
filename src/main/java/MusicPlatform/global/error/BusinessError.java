package MusicPlatform.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum BusinessError {
    //Album
    NOT_FOUND_ALBUM(HttpStatus.NOT_FOUND, "앨범을 찾을 수 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
