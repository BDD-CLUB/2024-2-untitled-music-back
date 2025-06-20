package MusicPlatform.domain.playlist.repository;

import MusicPlatform.domain.playlist.entity.Playlist;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    @Query("SELECT p FROM Playlist p "
            + "JOIN FETCH p.artist at "
            + "WHERE p.uuid = :uuid")
    Optional<Playlist> findByUuid(String uuid);

    @Query("SELECT p FROM Playlist p "
            + "JOIN FETCH p.artist at "
            + "WHERE at.uuid = :artistUuid")
    List<Playlist> findAllByArtistUuid(String artistUuid);
}
