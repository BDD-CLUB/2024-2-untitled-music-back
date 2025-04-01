package MusicPlatform.domain.artist.service.dto.response;

import MusicPlatform.domain.artist.entity.Artist;
import MusicPlatform.domain.artist.entity.Role;
import lombok.Builder;

@Builder
public record ArtistResponseDto(
        String uuid,
        String name,
        String description,
        String link1,
        String link2,
        int following,
        int follower,
        Role role,
        String email,
        String artistImage
) {
    public static ArtistResponseDto from(Artist artist) {
        return ArtistResponseDto.builder()
                .uuid(artist.getUuid())
                .name(artist.getName())
                .description(artist.getDescription())
                .link1(artist.getLink1())
                .link2(artist.getLink2())
                .follower(artist.getFollowers().size())
                .following(artist.getFollowings().size())
                .role(artist.getRole())
                .email(artist.getEmail())
                .artistImage(artist.getArtistImage())
                .build();
    }
}
