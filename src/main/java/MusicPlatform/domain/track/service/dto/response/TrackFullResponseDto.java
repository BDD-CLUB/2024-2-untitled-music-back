package MusicPlatform.domain.track.service.dto.response;

import MusicPlatform.domain.album.service.dto.response.AlbumBasicResponseDto;
import MusicPlatform.domain.artist.service.dto.response.ArtistResponseDto;
import MusicPlatform.domain.track.entity.Track;
import lombok.Builder;

@Builder
public record TrackFullResponseDto(
        TrackBasicResponseDto trackResponseDto,
        AlbumBasicResponseDto albumResponseDto,
        ArtistResponseDto artistResponseDto
) {
    public static TrackFullResponseDto from(Track track) {
        return TrackFullResponseDto.builder()
                .trackResponseDto(TrackBasicResponseDto.from(track))
                .albumResponseDto(AlbumBasicResponseDto.from(track.getAlbum()))
                .artistResponseDto(ArtistResponseDto.from(track.getArtist()))
                .build();
    }
}
