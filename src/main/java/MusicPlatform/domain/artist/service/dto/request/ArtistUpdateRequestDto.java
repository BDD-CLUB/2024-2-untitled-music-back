package MusicPlatform.domain.artist.service.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ArtistUpdateRequestDto(
        @NotBlank @Size(min = 1, max = 25)
        String name,
        @Nullable @Size(max = 225)
        String description,
        @Nullable @Size(max = 500)
        String link1,
        @Nullable @Size(max = 500)
        String link2
) {
}
