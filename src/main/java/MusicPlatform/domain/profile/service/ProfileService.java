package MusicPlatform.domain.profile.service;

import static MusicPlatform.global.error.BusinessError.NOT_FOUND_PROFILE;

import MusicPlatform.domain.artist.entity.Artist;
import MusicPlatform.domain.artist.service.ArtistService;
import MusicPlatform.domain.oauth2.service.CookieService;
import MusicPlatform.domain.profile.entity.Profile;
import MusicPlatform.domain.profile.repository.ProfileRepository;
import MusicPlatform.domain.profile.service.dto.request.ProfileRequestDto;
import MusicPlatform.domain.profile.service.dto.response.ProfileResponseDto;
import MusicPlatform.global.error.BusinessException;
import MusicPlatform.global.helper.AuthorizationHelper;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final AuthorizationHelper authorizationHelper;
    private final CookieService cookieService;
    private final ArtistService artistService;

    @Transactional(readOnly = true)
    public Profile getByUuid(String uuid) {
        return profileRepository.findByUuid(uuid).orElseThrow(
                () -> new BusinessException(NOT_FOUND_PROFILE)
        );
    }

    public void save(ProfileRequestDto requestDto) {
        String uuid = authorizationHelper.getMyUuid();
        Artist artist = artistService.findByUuid(uuid);
        Profile profile = Profile.builder()
                .name(requestDto.name())
                .description(requestDto.description())
                .link1(requestDto.link1())
                .link2(requestDto.link2())
                .profileImage(requestDto.profileImage())
                .isMain(requestDto.isMain())
                .artist(artist)
                .build();
        profileRepository.save(profile);
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

    @Transactional(readOnly = true)
    public ProfileResponseDto get(String uuid) {
        Profile profile = getByUuid(uuid);
        return ProfileResponseDto.from(profile);
    }

    @Transactional(readOnly = true)
    public List<ProfileResponseDto> getAll() {
        List<Profile> profiles = profileRepository.findAll();
        return profiles.stream().map(ProfileResponseDto::from).toList();
    }

    private Profile changeToMain(HttpServletResponse response) {
        log.info("profile 쿠키 존재하지 않음.");
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
