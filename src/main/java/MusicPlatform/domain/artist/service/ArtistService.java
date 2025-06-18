package MusicPlatform.domain.artist.service;

import static MusicPlatform.domain.artist.entity.Role.ROLE_USER;
import static MusicPlatform.global.error.BusinessError.NOT_FOUND_ARTIST;

import MusicPlatform.domain.artist.entity.Artist;
import MusicPlatform.domain.artist.repository.ArtistRepository;
import MusicPlatform.domain.artist.service.dto.request.ArtistUpdateRequestDto;
import MusicPlatform.domain.artist.service.dto.response.ArtistResponseDto;
import MusicPlatform.domain.oauth2.entity.ProviderUser;
import MusicPlatform.global.error.BusinessException;
import MusicPlatform.global.helper.AuthorizationHelper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final AuthorizationHelper authorizationHelper;

    @Transactional(readOnly = true)
    public Artist findByUuid(String uuid) {
        return artistRepository.findByUuid(uuid).orElseThrow(() ->
                new BusinessException(NOT_FOUND_ARTIST));
    }

    public Artist registerOrReturn(String registrationId, ProviderUser providerUser) {
        Artist existArtist = artistRepository.findByEmail(providerUser.getEmail());
        if (existArtist != null) { // 이미 가입한 회원 검증
            return existArtist;
        }

        Artist artist = Artist.builder()
                .email(providerUser.getEmail())
                .name(providerUser.getName())
                .provider(registrationId)
                .artistImage(providerUser.getPicture()) //todo 기본 이미지 등록
                .role(ROLE_USER)
                .build();

        artistRepository.save(artist);
        return artist;
    }

    public ArtistResponseDto getMyInfo() {
        String uuid = authorizationHelper.getMyUuid();
        log.info("uuid = " + uuid);
        Artist artist = findByUuid(uuid);
        return ArtistResponseDto.from(artist);
    }

    public ArtistResponseDto getByUuid(String uuid) {
        Artist artist = findByUuid(uuid);
        return ArtistResponseDto.from(artist);
    }

    public List<ArtistResponseDto> getAll(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("createdAt").descending());
        Page<Artist> artist = artistRepository.findAll(pageable);
        return artist.stream().map(ArtistResponseDto::from).toList();
    }

    //artist를 팔로하고 있는 모든(최근 20명) 회원을 구한다.
    @Transactional(readOnly = true)
    public List<ArtistResponseDto> findAllFollower(String artistUuid) {
        Pageable pageable = PageRequest.of(0, 20, Sort.by("createdAt").descending());
        Page<Artist> artists = artistRepository.findAllByFollowingIsArtist(artistUuid, pageable);
        return artists.stream().map(ArtistResponseDto::from).toList();
    }

    //artist가 팔로하고 있는 모든(최근 20명) 회원을 구한다.
    @Transactional(readOnly = true)
    public List<ArtistResponseDto> findAllFollowing(String artistUuid) {
        Pageable pageable = PageRequest.of(0, 20, Sort.by("createdAt").descending());
        Page<Artist> artists = artistRepository.findAllByFollowerIsArtist(artistUuid, pageable);
        return artists.stream().map(ArtistResponseDto::from).toList();
    }

    public ArtistResponseDto changeArtistImage(String newImage) {
        String uuid = authorizationHelper.getMyUuid();
        Artist artist = findByUuid(uuid);
        artist.updateImage(newImage);

        return ArtistResponseDto.from(artist);
    }

    public void updateByUuid(String uuid, ArtistUpdateRequestDto request) {
        Artist artist = findByUuid(uuid);
        artist.update(request.name(),
                request.link1(),
                request.link2(),
                request.description());
    }

    public void removeArtist() {
        //todo: 회원 탈퇴시 리소스는 어떻게 처리할 것인가?
        String uuid = authorizationHelper.getMyUuid();
        Artist artist = findByUuid(uuid);
        artistRepository.delete(artist);
    }
}
