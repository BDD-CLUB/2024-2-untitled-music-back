package MusicPlatform.domain.playlist._item.service;

import MusicPlatform.domain.playlist._item.entity.PlaylistItem;
import MusicPlatform.domain.playlist._item.repository.PlaylistItemRepository;
import MusicPlatform.domain.playlist.entity.Playlist;
import MusicPlatform.domain.track.entity.Track;
import MusicPlatform.domain.track.service.TrackService;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class PlaylistItemService {
    private final PlaylistItemRepository playlistItemRepository;
    private final TrackService trackService;

    @Transactional(readOnly = true)
    public List<PlaylistItem> findAllByPlaylist(Playlist playlist) {
        return playlistItemRepository.findAllByPlaylist(playlist);
    }

    public void save(Playlist playlist, String trackUuid) {
        Track track = trackService.getByUuid(trackUuid);
        PlaylistItem playlistItem = PlaylistItem.builder()
                .playlist(playlist)
                .track(track)
                .build();
        playlistItemRepository.save(playlistItem);
    }

    public void delete(String removedItemUuid) {
        playlistItemRepository.deleteByUuid(removedItemUuid); //hard delete
    }
}