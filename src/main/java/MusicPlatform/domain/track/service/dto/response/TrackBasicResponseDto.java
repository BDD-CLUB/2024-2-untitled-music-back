package MusicPlatform.domain.track.service.dto.response;

import MusicPlatform.domain.track.entity.Track;
import lombok.Builder;

@Builder
public record TrackBasicResponseDto(
        String uuid,
        String title,
        String lyric,
        String trackUrl,
        int duration,
        String artUrl
) {
    public static TrackBasicResponseDto from(Track track) {
        return TrackBasicResponseDto.builder()
                .uuid(track.getUuid())
                .title(track.getTitle())
                .trackUrl(track.getTrackUrl())
                .lyric(track.getLyric())
                .duration(track.getDuration())
                .artUrl(track.getAlbumArt())
                .build();
    }
}
