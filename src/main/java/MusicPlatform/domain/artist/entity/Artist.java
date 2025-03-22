package MusicPlatform.domain.artist.entity;

import MusicPlatform.domain.follow.entity.Follow;
import MusicPlatform.global.entity.UuidEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

// todo: 회원 탈퇴시 회원을 삭제하지 않고 이름을 '존재하지 않는 사용자'로 변경한다.

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

    private String link1;
    private String link2;
    private String description;

    @Column(nullable = false)
    private String provider; //todo: enum으로 수정

    @Column(nullable = false)
    private String artistImage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "follower", orphanRemoval = true)
    private Set<Follow> followers;
    @OneToMany(mappedBy = "following", orphanRemoval = true)
    private Set<Follow> followings;

    @Column(nullable = false)
    private boolean isDeleted;

    @Builder
    private Artist(String name, String email, String link1, String link2, String description, String provider, String artistImage, Role role) {
        this.name = name;
        this.email = email;
        this.link1 = link1;
        this.link2 = link2;
        this.description = description;
        this.provider = provider;
        this.artistImage = artistImage;
        this.role = role;
        this.isDeleted = false;
    }

    public void updateImage(String newImage) {
        this.artistImage = newImage;
    }

    public void update(String name, String link1, String link2, String description) {
        this.name = name;
        this.link1 = link1;
        this.link2 = link2;
        this.description = description;
    }
}
