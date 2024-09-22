package MusicPlatform.domain.member.entity;

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
@Table(name = "member")
@SQLRestriction("is_deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE member SET is_deleted = true where id = ?")
public class Member extends UuidEntity {

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String provider; //todo: enum으로 수정

    @Column(nullable = false)
    private String profileImage;

    @Column(nullable = false)
    private boolean isDeleted;

    @Builder
    private Member(String nickname, String email, String provider, String profileImage, boolean isDeleted) {
        this.nickname = nickname;
        this.email = email;
        this.provider = provider;
        this.profileImage = profileImage;
        this.isDeleted = isDeleted;
    }
}
