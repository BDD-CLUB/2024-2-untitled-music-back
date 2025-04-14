package MusicPlatform.domain.album.repository;

import MusicPlatform.domain.album.entity.Album;
import MusicPlatform.domain.artist.entity.Artist;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    @Query("SELECT a FROM Album a "
            + "JOIN FETCH a.artist at ")
    Page<Album> findAll(Pageable pageable);

    Optional<Album> findByUuid(String uuid);
    Page<Album> findAllByArtist(Artist artist, Pageable pageable);
}
