package MusicPlatform.domain.track;

import MusicPlatform.domain.album.entity.Album;
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
@Table(name = "track")
@SQLRestriction("is_deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE track SET is_deleted = true where id = ?")
public class Track extends UuidEntity {

    @Column(nullable = false)
    private String title;

    private String lyric;

    @Column(nullable = false)
    private String song_url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ALBUM_ID", nullable = false)
    private Album album;

    @Column(nullable = false)
    private boolean isDeleted;
}
