package MusicPlatform.domain.follow.service;

import static MusicPlatform.global.error.BusinessError.FORBIDDEN_FOLLOWING_ACCESS;
import static MusicPlatform.global.error.BusinessError.NOT_FOUND_FOLLOW;

import MusicPlatform.domain.artist.entity.Artist;
import MusicPlatform.domain.artist.service.ArtistService;
import MusicPlatform.domain.follow.entity.Follow;
import MusicPlatform.domain.follow.repository.FollowRepository;
import MusicPlatform.global.error.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final ArtistService artistService;

    public Follow findByUuid(String uuid) {
        return followRepository.findByUuid(uuid)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_FOLLOW));
    }

    // follow (artistUuid -> targetUuid)
    public void save(String artistUuid, String targetUuid) {
        Artist artist = artistService.findByUuid(artistUuid);
        Artist targetArtist =  artistService.findByUuid(targetUuid);
        Follow follow = new Follow(artist, targetArtist);
        followRepository.save(follow);
    }

    // unfollow
    public void delete(String artistUuid, String uuid) {
        Follow follow = findByUuid(uuid);
        if (!follow.getFollower().getUuid().equals(artistUuid)) {
            throw new BusinessException(FORBIDDEN_FOLLOWING_ACCESS);
        }
        followRepository.delete(follow);
    }
}
