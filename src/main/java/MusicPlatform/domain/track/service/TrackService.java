package MusicPlatform.domain.track.service;

import static MusicPlatform.global.error.BusinessError.NOT_FOUND_TRACK;

import MusicPlatform.domain.album.entity.Album;
import MusicPlatform.domain.artist.entity.Artist;
import MusicPlatform.domain.artist.service.ArtistService;
import MusicPlatform.domain.track.entity.Track;
import MusicPlatform.domain.track.repository.TrackRepository;
import MusicPlatform.domain.track.repository.dto.request.TrackRequestDto;
import MusicPlatform.domain.track.repository.dto.request.TrackUpdateRequestDto;
import MusicPlatform.domain.track.repository.dto.response.TrackGetResponseDto;
import MusicPlatform.global.error.BusinessException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TrackService {
    private final TrackRepository trackRepository;
    private final ArtistService artistService;

    @Transactional(readOnly = true)
    public Track getByUuid(String uuid) {
        return trackRepository.findByUuid(uuid).orElseThrow(() ->
                new BusinessException(NOT_FOUND_TRACK));
    }


    @Transactional(readOnly = true)
    public List<Track> getAllByAlbum(Album album) {
        return trackRepository.findAllByAlbum(album);
    }

    public void save(TrackRequestDto requestDto, Album album) {
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
    public TrackGetResponseDto getTrack(String uuid) {
        Track track = getByUuid(uuid);
        return TrackGetResponseDto.from(track);
    }

    @Deprecated // 앨범의 getAllByArtist로 대체한다.
    @Transactional(readOnly = true)
    public List<TrackGetResponseDto> getAllByArtist(String artistUuid) {
        Artist artist = artistService.findByUuid(artistUuid);
        List<Track> tracks = trackRepository.findAllByArtist(artist);
        return tracks.stream()
                .map(TrackGetResponseDto::from)
                .toList();
    }

    public void updateByUuid(TrackUpdateRequestDto requestDto, String uuid) {
        //todo: 인가 필요
        Track track = getByUuid(uuid);
        track.update(requestDto.title(), requestDto.lyric());
    }

    public void deleteByUuid(String uuid) {
        //todo: 인가 필요
        Track track = getByUuid(uuid);
        trackRepository.delete(track);
        //s3에 업로드된 파일도 삭제해야하는가? (복구 불가?)
    }
}
