package MusicPlatform.domain.track.entity;

import MusicPlatform.domain.album.entity.Album;
import MusicPlatform.domain.artist.entity.Artist;
import MusicPlatform.global.entity.UuidEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
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
//@SQLRestriction("is_deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE track SET is_deleted = true where id = ?")
@Table(name = "track", indexes = {
        @Index(name = "created_deleted_track_index", columnList = "created_at, is_deleted"),
})
public class Track extends UuidEntity {

    //todo: 앨범 내 노래 순서 설정?

    @Column(nullable = false)
    private String title;

    private String lyric;

    @Column(nullable = false)
    private String trackUrl;

    @Column(nullable = false)
    private int duration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ALBUM_ID", nullable = false)
    private Album album;

    @Column(nullable = false)
    private boolean isDeleted;

    @Builder
    private Track(String title, String lyric, String trackUrl, int duration, Album album) {
        this.title = title;
        this.lyric = lyric;
        this.trackUrl = trackUrl;
        this.duration = duration;
        this.album = album;
    }

    public String getAlbumArt() {
        return this.album.getArtImage();
    }

    public Artist getArtist() {
        return this.album.getArtist();
    }

    public void update(String title, String lyric) {
        this.title = title;
        this.lyric = lyric;
    }
}
