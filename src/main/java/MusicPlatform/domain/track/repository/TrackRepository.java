package MusicPlatform.domain.track.repository;

import MusicPlatform.domain.album.entity.Album;
import MusicPlatform.domain.artist.entity.Artist;
import MusicPlatform.domain.track.entity.Track;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TrackRepository extends JpaRepository<Track, Long> {

    Optional<Track> findByUuid(String uuid);

    @Query("SELECT t FROM Track t "
            + "JOIN t.profile p "
            + "JOIN p.artist a "
            + "WHERE a = :artist")
    List<Track> findAllByArtist(Artist artist);

    List<Track> findAllByAlbum(Album album);
}
