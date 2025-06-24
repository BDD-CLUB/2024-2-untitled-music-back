package MusicPlatform.domain.track.repository;

import MusicPlatform.domain.track.entity.Track;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TrackRepository extends JpaRepository<Track, Long> {

    @Query("SELECT t FROM Track t "
            + "LEFT JOIN FETCH t.album a "
            + "LEFT JOIN FETCH a.artist at "
            + "WHERE t.uuid = :uuid")
    Optional<Track> findByUuid(String uuid);

    @Query("SELECT t FROM Track t "
            + "LEFT JOIN FETCH t.album a "
            + "LEFT JOIN FETCH a.artist at "
            + "WHERE a.uuid = :artistUuid")
    Slice<Track> findAllByArtist(String artistUuid, Pageable pageable);

    @Query("SELECT t FROM Track t "
            + "LEFT JOIN FETCH t.album a "
            + "LEFT JOIN FETCH a.artist at ")
    Slice<Track> findAllBySlice(Pageable pageable);
}
