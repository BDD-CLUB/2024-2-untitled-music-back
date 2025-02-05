package MusicPlatform.service.playlist;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;

import MusicPlatform.domain.playlist.entity.Playlist;
import MusicPlatform.domain.playlist.repository.PlaylistRepository;
import MusicPlatform.domain.playlist.service.PlaylistService;
import jakarta.transaction.Transactional;
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
    private JdbcTemplate jdbcTemplate;
    @Autowired
    PlaylistService playlistService;
    @Autowired
    PlaylistRepository playlistRepository;

    @Test
    @DisplayName("Playlist 삭제 시 그 하위 종속 요소가 함께 삭제된다.")
    public void Playlist_삭제시_하위_종속_항목이_삭제된다() throws Exception {
        //given
        Playlist playlist = playlistRepository.findById(1L).orElseThrow();
        //when
        playlistService.deletePlaylist(playlist.getArtist().getUuid(), playlist.getUuid());
        playlistRepository.flush();
        //then
        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM playlist_item pi WHERE pi.playlist_id = ?",Integer.class, playlist.getId());
        assertTrue(count == 0 , "하위 종속 요소 PlaylistItem 삭제되지 않음");
    }
}
