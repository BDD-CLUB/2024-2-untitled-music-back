package MusicPlatform.domain.playlist._item.repository;

import MusicPlatform.domain.playlist._item.entity.PlaylistItem;
import MusicPlatform.domain.playlist.entity.Playlist;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistItemRepository extends JpaRepository<PlaylistItem,Long>  {
    Page<PlaylistItem> findAllByPlaylist(Playlist playlist, Pageable pageable);
    void deleteByUuid(String uuid);
}
