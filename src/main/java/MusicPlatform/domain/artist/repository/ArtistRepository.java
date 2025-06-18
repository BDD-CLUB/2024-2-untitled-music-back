package MusicPlatform.domain.artist.repository;

import MusicPlatform.domain.artist.entity.Artist;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Optional<Artist> findByUuid(String uuid);

    Artist findByEmail(String email);

    // Artist가 팔로하고 있는 모든 회원를 구한다.
    @Query("SELECT a FROM Artist a "
            + "Join Follow f on a = f.following "
            + "WHERE f.follower.uuid = :artistUuid ")
    Page<Artist> findAllByFollowerIsArtist(String artistUuid, Pageable pageable);

    // Artist를 팔로하고있는 모든 회원을 구한다.
    @Query("SELECT a FROM Artist a "
            + "Join Follow f on a = f.follower "
            + "WHERE f.following.uuid = :artistUuid ")
    Page<Artist> findAllByFollowingIsArtist(String artistUuid, Pageable pageable);
}
