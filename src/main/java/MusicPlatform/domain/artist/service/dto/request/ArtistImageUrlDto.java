package MusicPlatform.domain.artist.service.dto.request;

import jakarta.validation.constraints.Pattern;

public record ArtistImageUrlDto(
        @Pattern(regexp = "^https://.*")
        String imageUrl) {
}
