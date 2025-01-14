package MusicPlatform.domain.playlist.service.dto.response;

import MusicPlatform.domain.playlist._item.service.dto.response.PlaylistItemResponseDto;
import MusicPlatform.domain.playlist.entity.Playlist;
import java.util.List;
import lombok.Builder;

@Builder
public record PlaylistFullResponseDto(
        PlaylistBasicResponseDto playlistBasicResponseDto,
        List<PlaylistItemResponseDto> playlistItemResponseDtos
) {
    public static PlaylistFullResponseDto from(Playlist playlist, List<PlaylistItemResponseDto> playlistItemResponseDtos) {
        return PlaylistFullResponseDto.builder()
                .playlistBasicResponseDto(PlaylistBasicResponseDto.from(playlist))
                .playlistItemResponseDtos(playlistItemResponseDtos)
                .build();
    }
}
