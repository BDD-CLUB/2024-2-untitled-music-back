package MusicPlatform.domain.artist.service.dto.request;

public record ArtistUpdateRequestDto(
        String name,
        String description,
        String link1,
        String link2
) {
}
