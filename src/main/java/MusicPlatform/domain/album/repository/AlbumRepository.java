package MusicPlatform.domain.album.repository;

import MusicPlatform.domain.album.entity.Album;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    @Query("SELECT a FROM Album a "
            + "JOIN FETCH a.artist at ")
    Slice<Album> findAllBy(Pageable pageable);

    @Query("SELECT a FROM Album a "
            + "JOIN FETCH a.artist at "
            + "WHERE a.uuid = :uuid")
    Optional<Album> findByUuid(String uuid);

    @Query("SELECT a FROM Album a "
            + "JOIN FETCH a.artist at "
            + "WHERE at.uuid = :artistUuid")
    Slice<Album> findAllByArtistUuid(String artistUuid, Pageable pageable);
}
