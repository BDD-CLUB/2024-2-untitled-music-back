package MusicPlatform.domain.playlist.service.dto.response;

import MusicPlatform.domain.playlist.entity.Playlist;
import lombok.Builder;

@Builder
public record PlaylistBasicResponseDto(
        String uuid,
        String title,
        String description,
        String coverImageUrl
) {
    public static PlaylistBasicResponseDto from(Playlist playlist) {
        return PlaylistBasicResponseDto.builder()
                .uuid(playlist.getUuid())
                .title(playlist.getTitle())
                .description(playlist.getDescription())
                .coverImageUrl(playlist.getCoverImageUrl())
                .build();
    }
}
