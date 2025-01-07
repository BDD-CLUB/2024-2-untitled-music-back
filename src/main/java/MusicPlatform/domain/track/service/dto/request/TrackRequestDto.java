package MusicPlatform.domain.track.service.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public record TrackRequestDto(
        @NotBlank
        String title,

        @Nullable
        String lyric,

        @Min(value=1)
        @Max(value=3600, message="") //1시간
        int duration,

        @NotBlank
        String trackFile
) {
}
