package MusicPlatform.domain.playlist._item.repository;

import MusicPlatform.domain.playlist._item.entity.PlaylistItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistItemRepository extends JpaRepository<PlaylistItem,Long>  {
}
