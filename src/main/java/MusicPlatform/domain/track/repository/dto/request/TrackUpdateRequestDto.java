package MusicPlatform.domain.track.repository.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

public record TrackUpdateRequestDto (
        @NotBlank
        String title,

        @Nullable
        String lyric
){
}