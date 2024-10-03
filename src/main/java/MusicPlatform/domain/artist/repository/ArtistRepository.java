package MusicPlatform.domain.artist.repository;

import MusicPlatform.domain.artist.entity.Artist;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Optional<Artist> findByUuid(String uuid);

    Artist findByEmail(String email);
}
