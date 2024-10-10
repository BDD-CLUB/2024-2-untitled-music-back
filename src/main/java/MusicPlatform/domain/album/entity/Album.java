package MusicPlatform.domain.album.entity;

import MusicPlatform.domain.profile.entity.Profile;
import MusicPlatform.global.entity.UuidEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Profile_ID", nullable = true) //todo: false로 교체한다.
    private Profile profile;

    @Column(nullable = false)
    private boolean isDeleted;

    @Builder
    private Album(String title, String description, String artImage, LocalDate releaseDate, Profile profile) {
        this.title = title;
        this.description = description;
        this.artImage = artImage;
        this.releaseDate = releaseDate;
        this.profile = profile;
        this.isDeleted = false;
    }

    public void update(String artImage, String title, String description) {
        this.artImage = artImage;
        this.title = title;
        this.description = description;
    }
}
