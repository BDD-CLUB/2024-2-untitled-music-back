package MusicPlatform.service.album;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;

import MusicPlatform.domain.album.entity.Album;
import MusicPlatform.domain.album.repository.AlbumRepository;
import MusicPlatform.domain.album.service.AlbumService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Transactional
@Sql("/sql/service-test-data.sql")
public class AlbumServiceTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private AlbumRepository albumRepository;

    @Test
    @DisplayName("Album 삭제 시 그 하위 종속 요소가 함께 삭제된다.")
    public void Album_삭제_시_그_하위_종속_요소가_함께_삭제된다() throws Exception {
        //given
        Album album = albumRepository.findById(1L).orElseThrow();
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM track t where t.album_id = ? and t.is_deleted = false", Integer.class, album.getId());
        assertTrue(count > 0, "data.sql 정상 실행되지 않음");

        //when
        albumService.deleteByUuid(album.getUuid());
        albumRepository.flush();

        //then
        count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM track t where t.album_id = ? and t.is_deleted = false", Integer.class, album.getId());
        assertTrue(count == 0,"하위 종속 요소 Track 삭제되지 않음");
    }
}
