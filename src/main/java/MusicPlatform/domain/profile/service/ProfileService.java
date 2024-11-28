package MusicPlatform.domain.profile.service;

import static MusicPlatform.global.error.BusinessError.NOT_FOUND_PROFILE;

import MusicPlatform.domain.artist.entity.Artist;
import MusicPlatform.domain.oauth2.service.CookieService;
import MusicPlatform.domain.profile.entity.Profile;
import MusicPlatform.domain.profile.repository.ProfileRepository;
import MusicPlatform.domain.profile.service.dto.response.ProfileResponseDto;
import MusicPlatform.global.error.BusinessException;
import MusicPlatform.global.helper.AuthorizationHelper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final AuthorizationHelper authorizationHelper;
    private final CookieService cookieService;

    @Transactional(readOnly = true)
    public Profile getByUuid(String uuid) {
        return profileRepository.findByUuid(uuid).orElseThrow(
                () -> new BusinessException(NOT_FOUND_PROFILE)
        );
    }

    public void createProfile(Artist artist) {
        Profile profile = Profile.builder()
                .name(artist.getName())
                .profileImage(artist.getArtistImage())
                .artist(artist)
                .isMain(true)
                .build();
        profileRepository.save(profile);
    }

    @Transactional(readOnly = true)
    public ProfileResponseDto get(String uuid, HttpServletResponse response) {
        try {
            Profile profile = uuid.isBlank() ? changeToMain(response) : getByUuid(uuid);
            return ProfileResponseDto.from(profile);
        } catch (BusinessException e) {
            Profile profile = changeToMain(response);
            return ProfileResponseDto.from(profile);
        }
    }

    private Profile changeToMain(HttpServletResponse response) {
        String artistUuid = authorizationHelper.getMyUuid();
        Profile mainProfile = profileRepository.findByArtistUuidAndMainIsTrue(artistUuid);
        cookieService.saveProfileCookie(mainProfile.getUuid(), response);
        return mainProfile;
    }

    @Transactional(readOnly = true)
    public ProfileResponseDto change(String uuid, HttpServletResponse response) {
        Profile profile = getByUuid(uuid);
        cookieService.saveProfileCookie(uuid, response);
        return ProfileResponseDto.from(profile);
    }
}
