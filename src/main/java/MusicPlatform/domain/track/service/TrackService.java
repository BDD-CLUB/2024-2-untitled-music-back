package MusicPlatform.domain.track.service;

import static MusicPlatform.global.error.BusinessError.NOT_FOUND_TRACK;

import MusicPlatform.domain.album.entity.Album;
import MusicPlatform.domain.artist.entity.Artist;
import MusicPlatform.domain.artist.service.ArtistService;
import MusicPlatform.domain.track.entity.Track;
import MusicPlatform.domain.track.repository.TrackRepository;
import MusicPlatform.domain.track.service.dto.request.TrackRequestDto;
import MusicPlatform.domain.track.service.dto.request.TrackUpdateRequestDto;
import MusicPlatform.domain.track.service.dto.response.TrackFullResponseDto;
import MusicPlatform.global.error.BusinessException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Page<Track> getAllByAlbum(Album album, Pageable pageable) {
        return trackRepository.findAllByAlbum(album, pageable);
    }

    public void save(TrackRequestDto requestDto, Album album) {
        Track track = Track.builder()
                .title(requestDto.title())
                .lyric(requestDto.lyric())
                .duration(requestDto.duration())
                .album(album)
                .song_url(requestDto.trackFile())
                .build();
        trackRepository.save(track);
    }

    @Transactional(readOnly = true)
    public TrackFullResponseDto getTrack(String uuid) {
        Track track = getByUuid(uuid);
        return TrackFullResponseDto.from(track);
    }

    @Transactional(readOnly = true)
    public List<TrackFullResponseDto> getAll(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("createdAt").descending());
        Page<Track> tracks = trackRepository.findAll(pageable);
        return tracks.getContent().stream().map(TrackFullResponseDto::from).toList();
    }

    @Deprecated // 앨범의 getAllByArtist로 대체한다.
    @Transactional(readOnly = true)
    public List<TrackFullResponseDto> getAllByArtist(String artistUuid) {
        Artist artist = artistService.findByUuid(artistUuid);
        List<Track> tracks = trackRepository.findAllByArtist(artist);
        return tracks.stream()
                .map(TrackFullResponseDto::from)
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
