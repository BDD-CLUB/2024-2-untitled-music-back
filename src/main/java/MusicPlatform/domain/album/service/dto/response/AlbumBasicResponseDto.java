package MusicPlatform.domain.album.service.dto.response;

import MusicPlatform.domain.album.entity.Album;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record AlbumBasicResponseDto(
        String uuid,
        String title,
        String description,
        String artImage,
        LocalDate releaseDate
) {
    public static AlbumBasicResponseDto from(Album album) {
        return AlbumBasicResponseDto.builder()
                .uuid(album.getUuid())
                .title(album.getTitle())
                .description(album.getDescription())
                .artImage(album.getArtImage())
                .releaseDate(album.getReleaseDate())
                .build();
    }
}
