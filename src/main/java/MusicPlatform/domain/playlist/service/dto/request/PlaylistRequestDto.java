package MusicPlatform.domain.playlist.service.dto.request;

public record PlaylistRequestDto(
        String title,
        String description,
        String[] trackUuids
) {
}
