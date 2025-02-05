package MusicPlatform.domain.album.entity;

import MusicPlatform.domain.album._comment.entity.AlbumComment;
import MusicPlatform.domain.album._like.entity.AlbumLike;
import MusicPlatform.domain.artist.entity.Artist;
import MusicPlatform.domain.track.entity.Track;
import MusicPlatform.global.entity.UuidEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Table(name = "album")
@SQLRestriction("is_deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE album SET is_deleted = true where id = ?")
public class Album extends UuidEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String artImage;

    @Column(nullable = false)
    private LocalDate releaseDate;

    @OneToMany(mappedBy = "album", orphanRemoval = true)
    private List<AlbumComment> albumComments;

    @OneToMany(mappedBy = "album", orphanRemoval = true)
    private List<AlbumLike> albumLikes;
    
    @OneToMany(mappedBy = "album", orphanRemoval = true)
    private List<Track> tracks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ARTIST_ID", nullable = false)
    private Artist artist;

    @Column(nullable = false)
    private boolean isDeleted;

    @Builder
    private Album(String title, String description, String artImage, LocalDate releaseDate, Artist artist) {
        this.title = title;
        this.description = description;
        this.artImage = artImage;
        this.releaseDate = releaseDate;
        this.artist = artist;
        this.isDeleted = false;
    }

    public void update(String artImage, String title, String description) {
        this.artImage = artImage;
        this.title = title;
        this.description = description;
    }
}
