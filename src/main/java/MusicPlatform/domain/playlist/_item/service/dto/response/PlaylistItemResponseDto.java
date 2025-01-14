package MusicPlatform.domain.playlist._item.service.dto.response;

import MusicPlatform.domain.playlist._item.entity.PlaylistItem;
import MusicPlatform.domain.track.service.dto.response.TrackFullResponseDto;
import lombok.Builder;

@Builder
public record PlaylistItemResponseDto(
        String uuid,
       TrackFullResponseDto trackGetResponseDto
) {
    public static PlaylistItemResponseDto from(PlaylistItem playlistItem) {
        return PlaylistItemResponseDto.builder()
                .uuid(playlistItem.getUuid())
                .trackGetResponseDto(TrackFullResponseDto.from(playlistItem.getTrack()))
                .build();
    }
}
