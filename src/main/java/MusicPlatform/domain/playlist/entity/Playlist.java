package MusicPlatform.domain.playlist.entity;
import MusicPlatform.domain.member.entity.Member;

import MusicPlatform.global.entity.UuidEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

/**
 * 사용자 탈퇴시 플레이리스트는 보존된다?
 */

@Entity
@Getter
@Table(name = "playlist")
@SQLRestriction("is_deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE playlist SET is_deleted = true where id = ?")
public class Playlist extends UuidEntity {
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID", nullable = true)
    private Member member;

    @Column(nullable = false)
    private boolean isDeleted;
}
