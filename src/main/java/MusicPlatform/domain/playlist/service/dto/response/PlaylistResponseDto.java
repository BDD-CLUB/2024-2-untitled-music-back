package MusicPlatform.domain.playlist.service.dto.response;

import MusicPlatform.domain.playlist._item.service.dto.response.PlaylistItemResponseDto;
import MusicPlatform.domain.playlist.entity.Playlist;
import java.util.List;
import lombok.Builder;

@Builder
public record PlaylistResponseDto(
        String uuid,
        String title,
        String description,
        List<PlaylistItemResponseDto> playlistItemResponseDtos
) {
    public static PlaylistResponseDto from(Playlist playlist, List<PlaylistItemResponseDto> playlistItemResponseDtos) {
        return PlaylistResponseDto.builder()
                .uuid(playlist.getUuid())
                .title(playlist.getTitle())
                .description(playlist.getDescription())
                .playlistItemResponseDtos(playlistItemResponseDtos)
                .build();
    }
}
