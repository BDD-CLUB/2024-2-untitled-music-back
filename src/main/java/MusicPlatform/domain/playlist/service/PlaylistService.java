package MusicPlatform.domain.playlist.service;

import static MusicPlatform.global.error.BusinessError.NOT_FOUND_PLAYLIST;

import MusicPlatform.domain.artist.entity.Artist;
import MusicPlatform.domain.artist.service.ArtistService;
import MusicPlatform.domain.playlist._item.entity.PlaylistItem;
import MusicPlatform.domain.playlist._item.service.PlaylistItemService;
import MusicPlatform.domain.playlist._item.service.dto.request.PlaylistItemUpdateRequestDto;
import MusicPlatform.domain.playlist._item.service.dto.response.PlaylistItemResponseDto;
import MusicPlatform.domain.playlist.entity.Playlist;
import MusicPlatform.domain.playlist.repository.PlaylistRepository;
import MusicPlatform.domain.playlist.service.dto.request.PlaylistRequestDto;
import MusicPlatform.domain.playlist.service.dto.response.PlaylistResponseDto;
import MusicPlatform.domain.track.repository.dto.response.TrackResponseDto;
import MusicPlatform.global.error.BusinessException;
import MusicPlatform.global.helper.AuthorizationHelper;
import java.util.List;
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

    public void update(String uuid, PlaylistRequestDto requestDto) {
        Playlist playlist = findByUuid(uuid);
        playlist.update(requestDto.title(), requestDto.description());
    }

    public void update(String uuid, PlaylistItemUpdateRequestDto requestDto) {
        Playlist playlist = findByUuid(uuid);
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
    public PlaylistResponseDto getPlaylist(String uuid) {
        Playlist playlist = findByUuid(uuid);
        List<PlaylistItemResponseDto> playlistItemResponseDtos = playlistItemService.findAllByPlaylist(playlist)
                .stream()
                .map(PlaylistItemResponseDto::from)
                .toList();

        return PlaylistResponseDto.from(playlist, playlistItemResponseDtos);
    }

    public void deletePlaylist(String uuid) {
        Playlist playlist = findByUuid(uuid);
        playlistRepository.delete(playlist);
    }
}
