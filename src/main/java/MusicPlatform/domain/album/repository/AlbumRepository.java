package MusicPlatform.domain.album.repository;

import MusicPlatform.domain.album.entity.Album;
import MusicPlatform.domain.artist.entity.Artist;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    Optional<Album> findByUuid(String uuid);
    @Query("SELECT t FROM Album t "
            + "JOIN t.profile p "
            + "JOIN p.artist a "
            + "WHERE a = :artist")
    List<Album> getAllByArtist(Artist artist);
}
