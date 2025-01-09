package MusicPlatform.domain.album.service.dto.response;

import MusicPlatform.domain.artist.service.dto.response.ArtistResponseDto;
import MusicPlatform.domain.track.service.dto.response.TrackResponseDto;
import java.util.List;
import lombok.Builder;

@Builder
public record AlbumGetResponseDto(
        AlbumResponseDto albumResponseDto,
        List<TrackResponseDto> trackResponseDtos,
        ArtistResponseDto artistResponseDto
) {
}
