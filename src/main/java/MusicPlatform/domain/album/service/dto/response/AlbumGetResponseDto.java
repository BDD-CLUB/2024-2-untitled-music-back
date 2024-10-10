package MusicPlatform.domain.album.service.dto.response;

import MusicPlatform.domain.profile.service.dto.response.ProfileResponseDto;
import MusicPlatform.domain.track.repository.dto.response.TrackResponseDto;
import java.util.List;
import lombok.Builder;

@Builder
public record AlbumGetResponseDto(
        AlbumResponseDto albumResponseDto,
        List<TrackResponseDto> trackResponseDtos,
        ProfileResponseDto profileResponseDto
) {
}
