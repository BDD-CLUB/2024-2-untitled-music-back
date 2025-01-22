package MusicPlatform.domain.playlist.entity;
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

    private String coverImageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ARTIST_ID")
    private Artist artist;

    @Column(nullable = false)
    private boolean isDeleted;

    @Builder
    private Playlist(String title, String description, String coverImageUrl, Artist artist) {
        this.title = title;
        this.description = description;
        this.coverImageUrl = coverImageUrl;
        this.artist = artist;
    }

    public void updateCoverImage (String coverImgUrl) {
        this.coverImageUrl = coverImgUrl;
    }

    public void update(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
