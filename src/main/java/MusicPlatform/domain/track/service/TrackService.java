package MusicPlatform.domain.track.service;

import static MusicPlatform.global.error.BusinessError.NOT_FOUND_TRACK;

import MusicPlatform.domain.album.entity.Album;
import MusicPlatform.domain.album.service.AlbumService;
import MusicPlatform.domain.track.entity.Track;
import MusicPlatform.domain.track.repository.TrackRepository;
import MusicPlatform.domain.track.service.dto.request.TrackRequestDto;
import MusicPlatform.domain.track.service.dto.request.TrackUpdateRequestDto;
import MusicPlatform.domain.track.service.dto.response.TrackFullResponseDto;
import MusicPlatform.global.error.BusinessException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TrackService {
    private final TrackRepository trackRepository;
    private final AlbumService albumService;

    @Transactional(readOnly = true)
    public Track findByUuid(String uuid) {
        return trackRepository.findByUuid(uuid).orElseThrow(() ->
                new BusinessException(NOT_FOUND_TRACK));
    }

    public void save(TrackRequestDto requestDto, String albumUuid) {
        Album album = albumService.getByUuid(albumUuid);
        Track track = Track.builder()
                .title(requestDto.title())
                .lyric(requestDto.lyric())
                .duration(requestDto.duration())
                .album(album)
                .trackUrl(requestDto.trackFile())
                .build();
        trackRepository.save(track);
    }

    @Transactional(readOnly = true)
    public TrackFullResponseDto getByUuid(String uuid) {
        Track track = findByUuid(uuid);
        return TrackFullResponseDto.from(track);
    }

    @Transactional(readOnly = true)
    public List<TrackFullResponseDto> getAll(int pageNo, int pageSize, String sortBy, String direction) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(direction), sortBy));
        Slice<Track> tracks = trackRepository.findAllBySlice(pageable);
        return tracks.getContent().stream().map(TrackFullResponseDto::from).toList();
    }

    @Transactional(readOnly = true)
    public List<TrackFullResponseDto> getAllByArtist(String artistUuid, int pageNo, int pageSize, String sortBy, String direction) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(direction), sortBy));
        Slice<Track> tracks = trackRepository.findAllByArtist(artistUuid, pageable);
        return tracks.stream()
                .map(TrackFullResponseDto::from)
                .toList();
    }

    public void updateByUuid(TrackUpdateRequestDto requestDto, String uuid) {
        //todo: 인가 필요
        Track track = findByUuid(uuid);
        track.update(requestDto.title(), requestDto.lyric());
    }

    public void deleteByUuid(String uuid) {
        //todo: 인가 필요
        Track track = findByUuid(uuid);
        trackRepository.delete(track);
        //s3에 업로드된 파일도 삭제해야하는가? (복구 불가?)
    }
}
