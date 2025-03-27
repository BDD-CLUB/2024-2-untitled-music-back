package MusicPlatform.service.artist;

import static org.assertj.core.api.Assertions.assertThat;
import MusicPlatform.domain.artist.entity.Artist;
import MusicPlatform.domain.artist.repository.ArtistRepository;
import MusicPlatform.domain.artist.service.ArtistService;
import MusicPlatform.domain.artist.service.dto.response.ArtistResponseDto;
import jakarta.transaction.Transactional;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@Transactional
@SpringBootTest
@Sql("/sql/follow-test-data.sql")
public class ArtistServiceTest {
    @Autowired
    private ArtistService artistService;
    @Autowired
    private ArtistRepository artistRepository;

    @Test
    @DisplayName("아티스트를 팔로우하고 있는 모든 아티스트를 조회할 수 있다.")
    public void 아티스트를_팔로우하고_있는_모든_아티스트를_조회할_수_있다() throws Exception {
        //given
        Artist artist = artistRepository.findById(1L).orElseThrow();
        //when
        List<ArtistResponseDto> responseDtos = artistService.findAllFollowing(artist.getUuid());
        //then
        assertThat(responseDtos.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("아티스트가 팔로우하고 있는 모든 아티스트를 조회할 수 있다. ")
    public void 아티스트가_팔로우하고_있는_모든_아티스트를_조회할_수_있다_() throws Exception {
        //given
        Artist artist = artistRepository.findById(1L).orElseThrow();
        //when
        List<ArtistResponseDto> responseDtos = artistService.findAllFollower(artist.getUuid());
        //then
        assertThat(responseDtos.size()).isEqualTo(2);
    }
}
