package MusicPlatform.domain.playlist.service.dto.response;

import MusicPlatform.domain.artist.entity.Artist;
import MusicPlatform.domain.artist.service.dto.response.ArtistResponseDto;
import MusicPlatform.domain.playlist._item.service.dto.response.PlaylistItemResponseDto;
import MusicPlatform.domain.playlist.entity.Playlist;
import java.util.List;
import lombok.Builder;

@Builder
public record PlaylistFullResponseDto(
        PlaylistBasicResponseDto playlistBasicResponseDto,
        List<PlaylistItemResponseDto> playlistItemResponseDtos,
        ArtistResponseDto artistResponseDto
) {
    public static PlaylistFullResponseDto from(Playlist playlist, List<PlaylistItemResponseDto> playlistItemResponseDtos, Artist artist) {
        return PlaylistFullResponseDto.builder()
                .playlistBasicResponseDto(PlaylistBasicResponseDto.from(playlist))
                .playlistItemResponseDtos(playlistItemResponseDtos)
                .artistResponseDto(ArtistResponseDto.from(artist))
                .build();
    }
}
