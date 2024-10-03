package MusicPlatform.domain.artist.entity;

import MusicPlatform.global.entity.UuidEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Table(name = "artist")
@SQLRestriction("is_deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE artist SET is_deleted = true where id = ?")
public class Artist extends UuidEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String provider; //todo: enum으로 수정

    @Column(nullable = false)
    private String artistImage;

    @Column(nullable = false)
    private boolean isDeleted;

    @Builder
    private Artist(String name, String email, String provider, String artistImage) {
        this.name = name;
        this.email = email;
        this.provider = provider;
        this.artistImage = artistImage;
        this.isDeleted = false;
    }
}
