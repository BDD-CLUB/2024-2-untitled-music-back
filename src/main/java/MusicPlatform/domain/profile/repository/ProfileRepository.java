package MusicPlatform.domain.profile.repository;

import MusicPlatform.domain.artist.entity.Artist;
import MusicPlatform.domain.profile.entity.Profile;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Deprecated
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByUuid(String uuid);
    List<Profile> findAllByArtist(Artist artist);

    @Query("SELECT p from Profile p "
            + "WHERE p.artist.uuid = :artistUuid "
            + "AND p.isMain = true ")
    Profile findByArtistUuidAndMainIsTrue(String artistUuid);
}
