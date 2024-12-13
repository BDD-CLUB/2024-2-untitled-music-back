package MusicPlatform.domain.playlist._item.repository;

import MusicPlatform.domain.playlist._item.entity.PlaylistItem;
import MusicPlatform.domain.playlist.entity.Playlist;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistItemRepository extends JpaRepository<PlaylistItem,Long>  {
    List<PlaylistItem> findAllByPlaylist(Playlist playlist);
    void deleteByUuid(String uuid);
}
