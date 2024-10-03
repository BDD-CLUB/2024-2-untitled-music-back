package MusicPlatform.domain.profile.entity;

import MusicPlatform.domain.artist.entity.Artist;
import MusicPlatform.global.entity.UuidEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Table(name = "profile")
@SQLRestriction("is_deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE profile SET is_deleted = true where id = ?")
public class Profile extends UuidEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String profileImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ARTIST_ID", nullable = false)
    private Artist artist;

    @Column(nullable = false)
    private boolean isMain; // true: Artist 정보와 동일한 프로필 (메인 프로필)

    @Column(nullable = false)
    private boolean isDeleted;

    @Builder
    private Profile(String name, String profileImage, Artist artist, boolean isMain) {
        this.name = name;
        this.profileImage = profileImage;
        this.artist = artist;
        this.isMain = isMain;
        this.isDeleted = false;
    }
}
