package MusicPlatform.domain.artist.service;

import static MusicPlatform.global.error.BusinessError.NOT_FOUND_ARTIST;

import MusicPlatform.domain.artist.entity.Artist;
import MusicPlatform.domain.artist.repository.ArtistRepository;
import MusicPlatform.domain.oauth2.entity.ProviderUser;
import MusicPlatform.domain.profile.service.ProfileService;
import MusicPlatform.global.error.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final ProfileService profileService;

    @Transactional(readOnly = true)
    public Artist findByUuid(String uuid) {
        return artistRepository.findByUuid(uuid).orElseThrow(() ->
                new BusinessException(NOT_FOUND_ARTIST));
    }

    public Artist register(String registrationId, ProviderUser providerUser) {
        Artist existArtist = artistRepository.findByEmail(providerUser.getEmail());
        if (existArtist != null) { // 이미 가입한 회원 검증
            return existArtist;
        }

        Artist artist = Artist.builder()
                .email(providerUser.getEmail())
                .name(providerUser.getName())
                .provider(registrationId)
                .artistImage(providerUser.getPicture()) //todo 기본 이미지 등록
                .build();

        artistRepository.save(artist);
        profileService.createProfile(artist);
        return artist;
    }
}
