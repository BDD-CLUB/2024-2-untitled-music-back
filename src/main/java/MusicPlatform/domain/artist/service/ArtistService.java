package MusicPlatform.domain.artist.service;

import static MusicPlatform.global.error.BusinessError.NOT_FOUND_ARTIST;

import MusicPlatform.domain.artist.entity.Artist;
import MusicPlatform.domain.artist.repository.ArtistRepository;
import MusicPlatform.global.error.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;

    @Transactional(readOnly = true)
    public Artist findByUuid(String uuid) {
        return artistRepository.findByUuid(uuid).orElseThrow(() ->
                new BusinessException(NOT_FOUND_ARTIST));
    }
}
