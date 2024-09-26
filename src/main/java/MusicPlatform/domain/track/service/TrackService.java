package MusicPlatform.domain.track.service;

import MusicPlatform.domain.album.entity.Album;
import MusicPlatform.domain.album.service.AlbumService;
import MusicPlatform.domain.track.entity.Track;
import MusicPlatform.domain.track.repository.TrackRepository;
import MusicPlatform.domain.track.repository.dto.request.TrackRequestDto;
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
}
