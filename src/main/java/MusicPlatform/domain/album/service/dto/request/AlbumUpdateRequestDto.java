package MusicPlatform.domain.album.service.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

public record AlbumUpdateRequestDto(
        @NotBlank
        String albumArt,
        @NotBlank
        String title,
        @Nullable
        String description
) {
}
