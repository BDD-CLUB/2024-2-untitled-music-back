package MusicPlatform.domain.track.repository;

import MusicPlatform.domain.track.entity.Track;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRepository extends JpaRepository<Track, Long> {

    Optional<Track> findByUuid(String uuid);
}
