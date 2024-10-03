package MusicPlatform.domain.track.repository.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public record TrackRequestDto(
        @NotBlank
        String title,

        @Nullable
        String lyric,

         MultipartFile TrackFile
) {
}
