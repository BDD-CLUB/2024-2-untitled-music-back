package MusicPlatform.domain.album.service;

import static MusicPlatform.global.error.BusinessError.NOT_FOUND_ALBUM;

import MusicPlatform.domain.album.entity.Album;
import MusicPlatform.domain.album.repository.AlbumRepository;
import MusicPlatform.domain.album.service.dto.request.AlbumRequestDto;
import MusicPlatform.domain.album.service.dto.request.AlbumUpdateRequestDto;
import MusicPlatform.domain.album.service.dto.response.AlbumFullResponseDto;
import MusicPlatform.domain.album.service.dto.response.AlbumBasicResponseDto;
import MusicPlatform.domain.artist.entity.Artist;
import MusicPlatform.domain.artist.service.ArtistService;
import MusicPlatform.domain.artist.service.dto.response.ArtistResponseDto;
import MusicPlatform.domain.track.entity.Track;
import MusicPlatform.domain.track.repository.TrackRepository;
import MusicPlatform.domain.track.service.dto.response.TrackBasicResponseDto;
import MusicPlatform.global.error.BusinessException;
import java.time.LocalDate;
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
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final TrackRepository trackRepository;
    private final ArtistService artistService;

    @Transactional(readOnly = true)
    public Album getByUuid(String uuid) {
        return albumRepository.findByUuid(uuid)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_ALBUM));
    }

    public void save(AlbumRequestDto requestDto, String artistUuid) {
        Artist artist = artistService.findByUuid(artistUuid);
        Album album = Album.builder()
                .artImage(requestDto.albumArt())
                .title(requestDto.title())
                .description(requestDto.description())
                .artist(artist)
                .releaseDate(LocalDate.now())
                .build();
        albumRepository.save(album);
    }

    @Transactional(readOnly = true)
    public AlbumFullResponseDto getAlbum(String uuid, int pageNo, int pageSize) {
        Album album = getByUuid(uuid);
        Pageable trackPageable = PageRequest.of(pageNo, pageSize, Sort.by("createdAt").descending());
        return convertToDto(album, trackPageable);
    }

    @Transactional(readOnly = true)
    public List<AlbumFullResponseDto> getAll(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("createdAt").descending());
        Page<Album> albums = albumRepository.findAll(pageable);
        Pageable trackPageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());
        return albums.stream().map(album -> convertToDto(album, trackPageable)).toList();
    }

    @Transactional(readOnly = true)
    public List<AlbumBasicResponseDto> getAllByArtist(String uuid, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("createdAt").descending());
        Artist artist = artistService.findByUuid(uuid);
        Page<Album> albums = albumRepository.findAllByArtist(artist, pageable);

        return albums.stream()
                .map(AlbumBasicResponseDto::from)
                .toList();
    }

    public void updateByUuid(AlbumUpdateRequestDto requestDto, String uuid) {
        //todo: 내가 업로드한 앨범인지 확인한다.
        Album album = getByUuid(uuid);
        //todo: 예전 엘범아트 삭제
        album.update(requestDto.albumArt(), requestDto.title(), requestDto.description());
    }

    public void deleteByUuid(String uuid) {
        //todo: 내가 업로드한 앨범인지 확인한다.
        Album album = getByUuid(uuid);
        trackRepository.deleteAllByAlbum(album);
        albumRepository.delete(album);
    }

    private AlbumFullResponseDto convertToDto(Album album, Pageable trackPageable) {
        Page<Track> tracks = trackRepository.findAllByAlbum(album, trackPageable);
        return AlbumFullResponseDto.builder()
                .albumResponseDto(AlbumBasicResponseDto.from(album))
                .trackResponseDtos(tracks.stream()
                        .map(TrackBasicResponseDto::from)
                        .toList())
                .artistResponseDto(ArtistResponseDto.from(album.getArtist()))
                .build();
    }
}
