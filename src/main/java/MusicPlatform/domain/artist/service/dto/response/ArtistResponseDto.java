package MusicPlatform.domain.artist.service.dto.response;

import MusicPlatform.domain.artist.entity.Artist;
import MusicPlatform.domain.artist.entity.Role;
import lombok.Builder;

@Builder
public record ArtistResponseDto(
        String uuid,
        String name,
        Role role,
        String email,
        String artistImage
) {
    public static ArtistResponseDto from(Artist artist) {
        return ArtistResponseDto.builder()
                .uuid(artist.getUuid())
                .name(artist.getName())
                .role(artist.getRole())
                .email(artist.getEmail())
                .artistImage(artist.getArtistImage())
                .build();
    }
}
