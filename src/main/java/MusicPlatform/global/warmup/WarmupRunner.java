package MusicPlatform.global.warmup;

import MusicPlatform.domain.album.service.AlbumService;
import MusicPlatform.domain.artist.service.ArtistService;
import MusicPlatform.domain.playlist.service.PlaylistService;
import MusicPlatform.domain.track.service.TrackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class WarmupRunner implements ApplicationRunner {

    private final AlbumService albumService;
    private final TrackService trackService;
    private final ArtistService artistService;
    private final PlaylistService playlistService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            albumService.getAll(1,1);
            trackService.getAll(1,1, "createdAt", "desc");
            artistService.getAll(1,1);
            playlistService.getAll(1,1);
        } catch (Exception e) {
            log.error("Warm Up Error");
        }
    }
}
