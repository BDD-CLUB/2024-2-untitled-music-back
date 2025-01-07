package MusicPlatform.domain.playlist._item.service.dto.response;

import MusicPlatform.domain.playlist._item.entity.PlaylistItem;
import MusicPlatform.domain.track.service.dto.response.TrackResponseDto;
import lombok.Builder;

@Builder
public record PlaylistItemResponseDto(
        String uuid,
       TrackResponseDto track
) {
    public static PlaylistItemResponseDto from(PlaylistItem playlistItem) {
        return PlaylistItemResponseDto.builder()
                .uuid(playlistItem.getUuid())
                .track(TrackResponseDto.from(playlistItem.getTrack()))
                .build();
    }
}
