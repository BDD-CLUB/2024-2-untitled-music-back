package MusicPlatform.domain.track.repository.dto.response;

import MusicPlatform.domain.album.service.dto.response.AlbumResponseDto;
import MusicPlatform.domain.profile.service.dto.response.ProfileResponseDto;
import MusicPlatform.domain.track.entity.Track;
import lombok.Builder;

@Builder
public record TrackGetResponseDto(
        TrackResponseDto trackResponseDto,
        AlbumResponseDto albumResponseDto,
        ProfileResponseDto profileResponseDto
) {
    public static TrackGetResponseDto from(Track track) {
        return TrackGetResponseDto.builder()
                .trackResponseDto(TrackResponseDto.from(track))
                .albumResponseDto(AlbumResponseDto.from(track.getAlbum()))
                .profileResponseDto(ProfileResponseDto.from(track.getProfile()))
                .build();
    }
}
