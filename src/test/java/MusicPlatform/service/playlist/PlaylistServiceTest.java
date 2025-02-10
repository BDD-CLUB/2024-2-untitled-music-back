package MusicPlatform.service.playlist;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;

import MusicPlatform.domain.playlist._item.service.PlaylistItemService;
import MusicPlatform.domain.playlist._item.service.dto.response.PlaylistItemResponseDto;
import MusicPlatform.domain.playlist.entity.Playlist;
import MusicPlatform.domain.playlist.repository.PlaylistRepository;
import MusicPlatform.domain.playlist.service.PlaylistService;
import MusicPlatform.domain.track.entity.Track;
import MusicPlatform.domain.track.repository.TrackRepository;
import MusicPlatform.domain.track.service.TrackService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

@Transactional
@SpringBootTest
@Sql("/sql/service-test-data.sql")
public class PlaylistServiceTest {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    PlaylistService playlistService;
    @Autowired
    PlaylistItemService playlistItemService;
    @Autowired
    TrackService trackService;
    @Autowired
    PlaylistRepository playlistRepository;
    @Autowired
    TrackRepository trackRepository;

    @Test
    @DisplayName("Playlist 삭제 시 그 하위 종속 요소가 함께 삭제된다.")
    public void Playlist_삭제시_하위_종속_항목이_삭제된다() throws Exception {
        //given
        Playlist playlist = playlistRepository.findById(1L).orElseThrow();
        //when
        playlistService.deletePlaylist(playlist.getArtist().getUuid(), playlist.getUuid());
        playlistRepository.flush();
        //then
        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM playlist_item pi WHERE pi.playlist_id = ?",
                Integer.class, playlist.getId());
        assertTrue(count == 0, "하위 종속 요소 PlaylistItem 삭제되지 않음");
    }

    @Test
    @Transactional
    @DisplayName("Track 삭제시 해당 Track과 연관된 PlaylistItem의 상태를 disabled로 조회한다")
    public void Track_삭제시_해당_Track과_연관된_PlaylistItem의_상태를_disabled로_조회한다() throws Exception {
        //given
        Track track = trackRepository.findById(1L).orElseThrow();
        //when
        trackService.deleteByUuid(track.getUuid());
        System.out.println(track.isDeleted()); //false
        entityManager.flush();

        Playlist playlist = playlistRepository.findById(1L).orElseThrow();
        System.out.println(playlist.getPlaylistItems().get(0).getTrack().isDeleted()); // true

        //then
        List<PlaylistItemResponseDto> responseDtos = playlistItemService.convertToDto(playlist, null);
        boolean isDisabled = responseDtos.stream().filter(PlaylistItemResponseDto::isDisabled)
                .map(dto -> dto.trackGetResponseDto().trackResponseDto().uuid()).toList().contains(track.getUuid());

        assertTrue(isDisabled, "PlaylistItemDto의 필드가 disabled=true되지 않음");
    }
}
