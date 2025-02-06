package MusicPlatform.domain.playlist._item.service.dto.response;

import MusicPlatform.domain.playlist._item.entity.PlaylistItem;
import MusicPlatform.domain.track.service.dto.response.TrackFullResponseDto;
import lombok.Builder;

@Builder
public record PlaylistItemResponseDto(
        String uuid,
        Boolean isDisabled,
        TrackFullResponseDto trackGetResponseDto
) {
    public static PlaylistItemResponseDto from(PlaylistItem playlistItem) {
        // Track의 상태가 변경됨에 따라 연관된 모든 Playlist를 업데이트하기 보다는 Playlist 조회 시 필터링하도록 한다.
        return PlaylistItemResponseDto.builder()
                .uuid(playlistItem.getUuid())
                .isDisabled(playlistItem.getTrack().isDeleted())
                .trackGetResponseDto(TrackFullResponseDto.from(playlistItem.getTrack()))
                .build();
    }
}
