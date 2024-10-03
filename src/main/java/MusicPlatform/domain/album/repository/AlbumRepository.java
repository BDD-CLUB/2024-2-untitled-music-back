package MusicPlatform.domain.album.repository;

import MusicPlatform.domain.album.entity.Album;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    Optional<Album> findByUuid(String uuid);
}
