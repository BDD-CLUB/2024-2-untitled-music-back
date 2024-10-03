package MusicPlatform.domain.playlist._item.entity;

import MusicPlatform.domain.playlist.entity.Playlist;
import MusicPlatform.domain.track.entity.Track;
import MusicPlatform.global.entity.UuidEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * hard delete 된다.
 * - 소프트 딜리트시 사용자 탈퇴로 인한 제거와 플레이리스트 수정으로 인한 삭제를 구분할 수 없다.
 * - 사용자가 플레이리스트에서 아이템을 삭제시 Hard delete된다.
 * - 사용자가 탈퇴시 플레이리스트만 소프트 딜리트하고 아이템은 삭제하지 않는다.
 */

@Entity
@Getter
@Table(name = "playlist_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaylistItem extends UuidEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private Track track;

    @ManyToOne(fetch = FetchType.LAZY)
    private Playlist playlist;
}
