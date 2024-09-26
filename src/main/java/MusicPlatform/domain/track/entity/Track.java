package MusicPlatform.domain.track.entity;

import MusicPlatform.domain.album.entity.Album;
import MusicPlatform.domain.artist.entity.Artist;
import MusicPlatform.domain.profile.Profile;
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
@Table(name = "track")
@SQLRestriction("is_deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE track SET is_deleted = true where id = ?")
public class Track extends UuidEntity {

    //todo: 앨범 내 노래 순서 설정?

    @Column(nullable = false)
    private String title;

    private String lyric;

    @Column(nullable = false)
    private String song_url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ALBUM_ID", nullable = false)
    private Album album;

    @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "Profile_ID", nullable = false) // todo: 인가 구현 이후 주석 해제
    private Profile profile;

    @Column(nullable = false)
    private boolean isDeleted;

    @Builder
    private Track(String title, String lyric, String song_url, Album album, Profile profile) {
        this.title = title;
        this.lyric = lyric;
        this.song_url = song_url;
        this.album = album;
        this.profile = profile;
    }

    public String getAlbumArt() {
        return this.album.getArtImage();
    }

    public Artist getArtist() {
        return this.profile.getArtist();
    }
}
