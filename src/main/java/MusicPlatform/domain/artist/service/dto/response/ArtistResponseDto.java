package MusicPlatform.domain.artist.service.dto.response;

import MusicPlatform.domain.artist.entity.Artist;
import lombok.Builder;

@Builder
public record ArtistResponseDto(
        String uuid,
        String name,
        String email,
        String artistImage
) {
    public static ArtistResponseDto from(Artist artist) {
        return ArtistResponseDto.builder()
                .uuid(artist.getUuid())
                .name(artist.getName())
                .email(artist.getEmail())
                .artistImage(artist.getArtistImage())
                .build();
    }
}
