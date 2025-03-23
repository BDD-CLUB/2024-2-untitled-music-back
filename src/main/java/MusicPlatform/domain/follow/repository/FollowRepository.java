package MusicPlatform.domain.follow.repository;

import MusicPlatform.domain.follow.entity.Follow;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Optional<Follow> findByUuid(String uuid);
}
