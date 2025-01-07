package MusicPlatform.domain.playlist._item.service.dto.response;

import MusicPlatform.domain.playlist._item.entity.PlaylistItem;
import MusicPlatform.domain.track.service.dto.response.TrackGetResponseDto;
import lombok.Builder;

@Builder
public record PlaylistItemResponseDto(
        String uuid,
       TrackGetResponseDto trackGetResponseDto
) {
    public static PlaylistItemResponseDto from(PlaylistItem playlistItem) {
        return PlaylistItemResponseDto.builder()
                .uuid(playlistItem.getUuid())
                .trackGetResponseDto(TrackGetResponseDto.from(playlistItem.getTrack()))
                .build();
    }
}
