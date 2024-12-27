package MusicPlatform.domain.artist.service;

import static MusicPlatform.domain.artist.entity.Role.ROLE_USER;
import static MusicPlatform.global.error.BusinessError.NOT_FOUND_ARTIST;

import MusicPlatform.domain.artist.entity.Artist;
import MusicPlatform.domain.artist.repository.ArtistRepository;
import MusicPlatform.domain.artist.service.dto.response.ArtistResponseDto;
import MusicPlatform.domain.oauth2.entity.ProviderUser;
import MusicPlatform.domain.profile.service.ProfileService;
import MusicPlatform.global.error.BusinessException;
import MusicPlatform.global.helper.AuthorizationHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final ProfileService profileService;
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
        profileService.createProfile(artist);
        return artist;
    }

    public ArtistResponseDto getMyInfo() {
        String uuid = authorizationHelper.getMyUuid();
        log.info("uuid = " + uuid);
        Artist artist = findByUuid(uuid);
        return ArtistResponseDto.from(artist);
    }

    @Transactional
    public ArtistResponseDto changeArtistImage(String newImage) {

        String uuid = authorizationHelper.getMyUuid();
        Artist artist = findByUuid(uuid);
        artist.updateImage(newImage);

        return ArtistResponseDto.from(artist);
    }


    public void removeArtist(){
        String uuid = authorizationHelper.getMyUuid();
        Artist artist = findByUuid(uuid);
        artist.deleteArtist();
        artistRepository.save(artist);
    }


}
