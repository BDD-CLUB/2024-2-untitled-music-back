package MusicPlatform.domain.track.service;

import static MusicPlatform.global.error.BusinessError.NOT_FOUND_TRACK;

import MusicPlatform.domain.album.entity.Album;
import MusicPlatform.domain.album.service.AlbumService;
import MusicPlatform.domain.track.entity.Track;
import MusicPlatform.domain.track.repository.TrackRepository;
import MusicPlatform.domain.track.repository.dto.request.TrackRequestDto;
import MusicPlatform.domain.track.repository.dto.response.TrackResponseDto;
import MusicPlatform.global.error.BusinessException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class TrackService {

    private final TrackRepository trackRepository;
    private final AlbumService albumService;


    @Transactional(readOnly = true)
    public Track getByUuid(String uuid) {
        return trackRepository.findByUuid(uuid).orElseThrow(() ->
                new BusinessException(NOT_FOUND_TRACK));
    }

    public void save(TrackRequestDto requestDto, MultipartFile file, String albumUuid) {
        Album album = albumService.getByUuid(albumUuid);

        // String songUrl = s3Service.uploadToS3(); //todo:  s3 파일 업로드 구현
        Track track = Track.builder()
                .title(requestDto.title())
                .lyric(requestDto.lyric())
                .profile(null) //todo: 인가 구현
                .album(album)
                .song_url("temp url")
                .build();

        trackRepository.save(track);
    }
    
    @Transactional(readOnly = true)
    public TrackResponseDto getTrack(String uuid) {
        Track track = getByUuid(uuid);
        return TrackResponseDto.from(track);
    }

    @Transactional(readOnly = true)
    public List<TrackResponseDto> getAllByArtist(String uuid) {

    }
}
