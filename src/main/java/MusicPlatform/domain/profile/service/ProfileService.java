package MusicPlatform.domain.profile.service;

import static MusicPlatform.global.error.BusinessError.FORBIDDEN_PROFILE_ACCESS;
import static MusicPlatform.global.error.BusinessError.NOT_FOUND_PROFILE;
import static MusicPlatform.global.error.BusinessError.ZERO_PROFILE_REQUEST;

import MusicPlatform.domain.artist.entity.Artist;
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
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final AuthorizationHelper authorizationHelper;
    private final CookieService cookieService;

    @Transactional(readOnly = true)
    public Profile findByUuid(String uuid) {
        return profileRepository.findByUuid(uuid).orElseThrow(
                () -> new BusinessException(NOT_FOUND_PROFILE)
        );
    }

//    @Transactional(readOnly = true)
//    public List<Profile> findAllByArtist(Artist artistUuid) {
//        Artist artist = artistService.findByUuid(artistUuid);
//        return profileRepository.findAllByArtist(artist);
//    }

    //create
    public void save(ProfileRequestDto requestDto, Artist artist) {
        String uuid = authorizationHelper.getMyUuid();
        //Artist artist = artistService.findByUuid(uuid);
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

    //read
    @Transactional(readOnly = true)
    public ProfileResponseDto get(String uuid, HttpServletResponse response) {
        try {
            Profile profile = uuid.isBlank() ? changeToMain(response) : findByUuid(uuid);
            return ProfileResponseDto.from(profile);
        } catch (BusinessException e) {
            Profile profile = changeToMain(response);
            return ProfileResponseDto.from(profile);
        }
    }

    @Transactional(readOnly = true)
    public List<ProfileResponseDto> getAll(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("createdAt").descending());
        Page<Profile> profiles = profileRepository.findAll(pageable);
        return profiles.getContent().stream().map(ProfileResponseDto::from).toList();
    }

    @Transactional(readOnly = true)
    public ProfileResponseDto getByUuid(String uuid) {
        Profile profile = findByUuid(uuid);
        return ProfileResponseDto.from(profile);
    }

    @Transactional(readOnly = true)
    public List<ProfileResponseDto> getAllByArtist(Artist artist) {
        //List<Profile> profiles = findAllByArtist(artist);
        List<Profile> profiles = profileRepository.findAllByArtist(artist);
        return profiles.stream().map(ProfileResponseDto::from).toList();
    }

    // update
    private Profile changeToMain(HttpServletResponse response) {
        log.info("profile 쿠키 존재하지 않음.");
        String artistUuid = authorizationHelper.getMyUuid();
        Profile mainProfile = profileRepository.findByArtistUuidAndMainIsTrue(artistUuid);
        cookieService.saveProfileCookie(mainProfile.getUuid(), response);
        return mainProfile;
    }

    @Transactional(readOnly = true)
    public ProfileResponseDto change(String uuid, HttpServletResponse response) {
        Profile profile = findByUuid(uuid);
        cookieService.saveProfileCookie(uuid, response);
        return ProfileResponseDto.from(profile);
    }

    public void updateByUuid(String artistUuid, String uuid, ProfileResponseDto request) {
        Profile profile = findByUuid(uuid);
        isAuthenticated(artistUuid, profile.getArtist().getUuid());
        profile.update(request.name(),
                request.description(),
                request.link1(),
                request.link2(),
                request.isMain());
    }

    public void updateProfileImage(String artistUuid, String uuid, String profileImageLink) {
        Profile profile = findByUuid(uuid);
        isAuthenticated(artistUuid, profile.getArtist().getUuid());
        profile.updateProfileImage(profileImageLink);
    }

    //delete
    public void delete(String artistUuid, String uuid, Artist artist) {
        Profile profile = findByUuid(uuid);
        isAuthenticated(artistUuid, profile.getArtist().getUuid());
        if ( profileRepository.findAllByArtist(artist).size() == 1) {
            throw new BusinessException(ZERO_PROFILE_REQUEST);
        }
        profileRepository.delete(profile);
    }

    private void isAuthenticated(String artistUuid, String profileArtistUuid) {
        if (!profileArtistUuid.equals(artistUuid)) {
            throw new BusinessException(FORBIDDEN_PROFILE_ACCESS);
        }
    }
}
