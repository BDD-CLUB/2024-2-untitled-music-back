package MusicPlatform.domain.playlist.service;

import MusicPlatform.domain.artist.entity.Artist;
import MusicPlatform.domain.artist.service.ArtistService;
import MusicPlatform.domain.playlist._item.service.PlaylistItemService;
import MusicPlatform.domain.playlist.entity.Playlist;
import MusicPlatform.domain.playlist.repository.PlaylistRepository;
import MusicPlatform.domain.playlist.service.dto.request.PlaylistRequestDto;
import MusicPlatform.global.helper.AuthorizationHelper;
import jakarta.transaction.Transactional;
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
}
