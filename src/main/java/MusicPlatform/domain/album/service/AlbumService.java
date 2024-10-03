package MusicPlatform.domain.album.service;

import static MusicPlatform.global.error.BusinessError.NOT_FOUND_ALBUM;

import MusicPlatform.domain.album.entity.Album;
import MusicPlatform.domain.album.repository.AlbumRepository;
import MusicPlatform.global.error.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;

    @Transactional(readOnly = true)
    public Album getByUuid(String uuid) {
        return albumRepository.findByUuid(uuid)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_ALBUM));
    }
}
