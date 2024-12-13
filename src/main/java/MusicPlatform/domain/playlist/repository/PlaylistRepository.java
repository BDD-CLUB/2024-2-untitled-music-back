package MusicPlatform.domain.playlist.repository;

import MusicPlatform.domain.playlist.entity.Playlist;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    Optional<Playlist> findByUuid(String uuid);
}
