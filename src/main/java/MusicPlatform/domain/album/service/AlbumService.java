package MusicPlatform.domain.album.service;

import static MusicPlatform.global.error.BusinessError.NOT_FOUND_ALBUM;

import MusicPlatform.domain.album.entity.Album;
import MusicPlatform.domain.album.repository.AlbumRepository;
import MusicPlatform.domain.album.service.dto.request.AlbumRequestDto;
import MusicPlatform.domain.album.service.dto.request.AlbumUpdateRequestDto;
import MusicPlatform.domain.album.service.dto.response.AlbumGetResponseDto;
import MusicPlatform.domain.album.service.dto.response.AlbumResponseDto;
import MusicPlatform.domain.artist.entity.Artist;
import MusicPlatform.domain.artist.service.ArtistService;
import MusicPlatform.domain.profile.service.dto.response.ProfileResponseDto;
import MusicPlatform.domain.track.entity.Track;
import MusicPlatform.domain.track.repository.dto.response.TrackResponseDto;
import MusicPlatform.domain.track.service.TrackService;
import MusicPlatform.global.error.BusinessException;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final TrackService trackService;
    private final ArtistService artistService;

    @Transactional(readOnly = true)
    public Album getByUuid(String uuid) {
        return albumRepository.findByUuid(uuid)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_ALBUM));
    }

    public void save(AlbumRequestDto requestDto) {
        //todo: s3 이미지 업로드 구현
        Album album = Album.builder()
                .artImage("")
                .title(requestDto.title())
                .description(requestDto.description())
                //.profile() //todo 현재 프로필을 가져온다.
                .releaseDate(LocalDate.now())
                .build();
        albumRepository.save(album);
    }

    @Transactional(readOnly = true)
    public AlbumGetResponseDto getAlbum(String uuid) {
        Album album = getByUuid(uuid);
        List<Track> tracks = trackService.getAllByAlbum(album);

        return AlbumGetResponseDto.builder()
                .albumResponseDto(AlbumResponseDto.from(album))
                .trackResponseDtos(tracks.stream()
                        .map(TrackResponseDto::from)
                        .toList())
                .profileResponseDto(ProfileResponseDto.from(album.getProfile()))
                .build();
    }

    @Transactional(readOnly = true)
    public List<AlbumResponseDto> getAllByArtist(String uuid) {
        Artist artist = artistService.findByUuid(uuid);
        List<Album> albums = albumRepository.getAllByArtist(artist);

        return albums.stream()
                .map(AlbumResponseDto::from)
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
        albumRepository.delete(album);
    }
}
