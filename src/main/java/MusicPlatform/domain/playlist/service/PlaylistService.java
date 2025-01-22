package MusicPlatform.domain.playlist.service;

import static MusicPlatform.global.error.BusinessError.FORBIDDEN_PLAYLIST_ACCESS;
import static MusicPlatform.global.error.BusinessError.NOT_FOUND_PLAYLIST;

import MusicPlatform.domain.artist.entity.Artist;
import MusicPlatform.domain.artist.service.ArtistService;
import MusicPlatform.domain.playlist._item.service.PlaylistItemService;
import MusicPlatform.domain.playlist._item.service.dto.request.PlaylistItemUpdateRequestDto;
import MusicPlatform.domain.playlist._item.service.dto.response.PlaylistItemResponseDto;
import MusicPlatform.domain.playlist.entity.Playlist;
import MusicPlatform.domain.playlist.repository.PlaylistRepository;
import MusicPlatform.domain.playlist.service.dto.request.PlaylistRequestDto;
import MusicPlatform.domain.playlist.service.dto.response.PlaylistFullResponseDto;
import MusicPlatform.global.error.BusinessException;
import MusicPlatform.global.helper.AuthorizationHelper;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final AuthorizationHelper authorizationHelper;
    private final ArtistService artistService;
    private final PlaylistItemService playlistItemService;

    @Transactional(readOnly = true)
    public Playlist findByUuid(String uuid) {
        return playlistRepository.findByUuid(uuid)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_PLAYLIST));
    }

    public void save(PlaylistRequestDto requestDto) {
        String artistUuid = authorizationHelper.getMyUuid();
        Artist artist = artistService.findByUuid(artistUuid);
        Playlist playlist = Playlist.builder()
                .title(requestDto.title())
                .description(requestDto.description())
                .artist(artist)
                .build();
        playlistRepository.save(playlist);

        for (String trackUuid : requestDto.trackUuids()) {
            playlistItemService.save(playlist, trackUuid);
        }
    }

    public void update(String artistUuid, String uuid, PlaylistRequestDto requestDto) {
        Playlist playlist = findByUuid(uuid);
        isAuthenticated(artistUuid, playlist.getArtist().getUuid());
        playlist.update(requestDto.title(), requestDto.description());
    }

    public void updateCoverImage(String artistUuid, String uuid, String coverImageLink) {
        Playlist playlist = findByUuid(uuid);
        isAuthenticated(artistUuid, playlist.getArtist().getUuid());
        playlist.updateCoverImage(coverImageLink);
    }


    public void update(String artistUuid, String uuid, PlaylistItemUpdateRequestDto requestDto) {
        Playlist playlist = findByUuid(uuid);
        isAuthenticated(artistUuid, playlist.getArtist().getUuid());

        String[] removedItemUuids = requestDto.removedItemUuids();
        String[] newTrackUuids = requestDto.newTrackUuids();

        for (String removedItemUuid : removedItemUuids) {
            playlistItemService.delete(removedItemUuid);
        }
        for (String newTrackUuid : newTrackUuids) {
            playlistItemService.save(playlist, newTrackUuid);
        }
    }

    @Transactional(readOnly = true)
    public PlaylistFullResponseDto getPlaylist(String uuid, int itemPageNo, int itemPageSize) {
        Playlist playlist = findByUuid(uuid);
        Pageable itemPageable = PageRequest.of(itemPageNo, itemPageSize, Sort.by("createdAt").descending());
        List<PlaylistItemResponseDto> playlistItemResponseDtos = playlistItemService.convertToDto(playlist,
                itemPageable);
        return PlaylistFullResponseDto.from(playlist, playlistItemResponseDtos);
    }

    @Transactional(readOnly = true)
    public List<PlaylistFullResponseDto> getAll(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("createdAt").descending());
        Page<Playlist> playlists = playlistRepository.findAll(pageable);
        Pageable itemPageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());

        return playlists.stream()
                .map(playlist -> {
                    List<PlaylistItemResponseDto> playlistItemResponseDtos = playlistItemService.convertToDto(playlist,
                            itemPageable);
                    return PlaylistFullResponseDto.from(playlist, playlistItemResponseDtos);
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PlaylistFullResponseDto> getAllByArtist(String uuid) {
        Artist artist = artistService.findByUuid(uuid);
        List<Playlist> playlists = playlistRepository.findAllByArtist(artist);
        Pageable itemPageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());

        return playlists.stream()
                .map(playlist -> {
                    List<PlaylistItemResponseDto> playlistItemResponseDtos = playlistItemService.convertToDto(playlist,
                            itemPageable);
                    return PlaylistFullResponseDto.from(playlist, playlistItemResponseDtos);
                })
                .toList();
    }

    public void deletePlaylist(String artistUuid, String uuid) {
        Playlist playlist = findByUuid(uuid);
        isAuthenticated(artistUuid, playlist.getArtist().getUuid());
        playlistRepository.delete(playlist);
    }

    private void isAuthenticated(String artistUuid, String playlistArtistUuid) {
        if (!playlistArtistUuid.equals(artistUuid)) {
            throw new BusinessException(FORBIDDEN_PLAYLIST_ACCESS);
        }
    }
}
