package MusicPlatform.domain.artist._member.entity;

import MusicPlatform.domain.artist.entity.Artist;
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

@Entity
@Getter
@Table(name = "artist_member")
@SQLRestriction("is_deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE artist_member SET is_deleted = true where id = ?")
public class ArtistMember extends UuidEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ARTIST_ID", nullable = false)
    private Artist artist;

    @Column(nullable = false)
    private boolean isDeleted;
}
