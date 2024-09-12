package MusicPlatform.domain.album._like.entity;


import MusicPlatform.domain.album.entity.Album;
import MusicPlatform.domain.member.entity.Member;
import MusicPlatform.global.entity.UuidEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 좋아요 취소 및 회원 탈퇴시 hard delete, 앨범 삭제시 미삭제
 */

@Entity
@Getter
@Table(name = "album_like")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlbumLike extends UuidEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ALBUM_ID", nullable = false)
    private Album album;
}
