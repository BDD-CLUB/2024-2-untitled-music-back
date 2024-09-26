package MusicPlatform.domain.track.repository.dto.response;

import MusicPlatform.domain.album.service.dto.response.AlbumResponseDto;
import MusicPlatform.domain.artist.service.dto.response.ArtistResponseDto;
import MusicPlatform.domain.track.entity.Track;
import lombok.Builder;

@Builder
public record TrackResponseDto(
        String uuid,
        String title,
        String lyric,
        String artUrl,
        AlbumResponseDto album,
        ArtistResponseDto artist
) {
    public static TrackResponseDto from(Track track) {
        return TrackResponseDto.builder()
                .uuid(track.getUuid())
                .title(track.getTitle())
                .lyric(track.getLyric())
                .artUrl(track.getAlbumArt())
                .album(AlbumResponseDto.from(track.getAlbum()))
                .artist(ArtistResponseDto.from(track.getArtist()))
                .build();
    }
}
