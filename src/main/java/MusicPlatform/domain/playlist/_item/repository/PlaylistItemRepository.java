package MusicPlatform.domain.playlist._item.repository;

import MusicPlatform.domain.playlist._item.entity.PlaylistItem;
import MusicPlatform.domain.playlist.entity.Playlist;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PlaylistItemRepository extends JpaRepository<PlaylistItem,Long>  {
    @Query("SELECT p FROM PlaylistItem p "
            + "JOIN FETCH p.track t "
            + "JOIN FETCH t.album a "
            + "JOIN FETCH a.artist at "
            + "WHERE p.playlist = :playlist")
    Slice<PlaylistItem> findAllByPlaylist(Playlist playlist, Pageable pageable);
    void deleteByUuid(String uuid);
}
