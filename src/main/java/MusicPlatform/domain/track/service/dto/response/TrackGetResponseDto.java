package MusicPlatform.domain.track.service.dto.response;

import MusicPlatform.domain.album.service.dto.response.AlbumResponseDto;
import MusicPlatform.domain.artist.service.dto.response.ArtistResponseDto;
import MusicPlatform.domain.track.entity.Track;
import lombok.Builder;

@Builder
public record TrackGetResponseDto(
        TrackResponseDto trackResponseDto,
        AlbumResponseDto albumResponseDto,
        ArtistResponseDto artistResponseDto
) {
    public static TrackGetResponseDto from(Track track) {
        return TrackGetResponseDto.builder()
                .trackResponseDto(TrackResponseDto.from(track))
                .albumResponseDto(AlbumResponseDto.from(track.getAlbum()))
                .artistResponseDto(ArtistResponseDto.from(track.getArtist()))
                .build();
    }
}
