package MusicPlatform.domain.album.service.dto.response;

import MusicPlatform.domain.artist.service.dto.response.ArtistResponseDto;
import MusicPlatform.domain.track.service.dto.response.TrackBasicResponseDto;
import java.util.List;
import lombok.Builder;

@Builder
public record AlbumFullResponseDto(
        AlbumBasicResponseDto albumResponseDto,
        List<TrackBasicResponseDto> trackResponseDtos,
        ArtistResponseDto artistResponseDto
) {
}
