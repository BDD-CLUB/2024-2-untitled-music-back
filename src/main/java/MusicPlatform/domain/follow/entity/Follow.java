package MusicPlatform.domain.follow.entity;

import MusicPlatform.domain.artist.entity.Artist;
import MusicPlatform.global.entity.UuidEntity;
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
@Table(name = "follow")
@SQLRestriction("is_deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE follow SET is_deleted = true where id = ?")
public class Follow  extends UuidEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FOLLOWIER_ID", nullable = false)
    private Artist follower; //팔로 하는 사람

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FOLLOWING_ID", nullable = false)
    private Artist following; //팔로 할 사람

    public Follow(Artist follower, Artist following) {
        this.follower = follower;
        this.following = following;
    }
}
