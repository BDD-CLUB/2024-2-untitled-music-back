package MusicPlatform.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum BusinessError {
    //Artist
    NOT_FOUND_ARTIST(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),

    //Album
    NOT_FOUND_ALBUM(HttpStatus.NOT_FOUND, "앨범을 찾을 수 없습니다."),

    //Track
    NOT_FOUND_TRACK(HttpStatus.NOT_FOUND, "트랙을 찾을 수 없습니다."),

    //Playlist
    NOT_FOUND_PLAYLIST(HttpStatus.NOT_FOUND, "플레이리스트를 찾을 수 없습니다."),
    FORBIDDEN_PLAYLIST_ACCESS(HttpStatus.FORBIDDEN, "플레이리스트 접근 권한이 없습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
