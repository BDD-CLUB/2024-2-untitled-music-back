package MusicPlatform.domain.profile.service;

import MusicPlatform.domain.artist.entity.Artist;
import MusicPlatform.domain.profile.entity.Profile;
import MusicPlatform.domain.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    public void createProfile(Artist artist) {
        Profile profile = Profile.builder()
                .name(artist.getName())
                .profileImage(artist.getArtistImage())
                .artist(artist)
                .isMain(true)
                .build();
        profileRepository.save(profile);
    }
}
