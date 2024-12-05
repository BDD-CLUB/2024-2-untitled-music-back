package MusicPlatform.domain.playlist._item.service.dto.request;

public record PlaylistItemUpdateRequestDto(
        String[] removedItemUuids, //playlistItem uuids
        String[] newTrackUuids //track uuids
) {
}
