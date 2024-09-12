package MusicPlatform.global.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import java.util.UUID;
import lombok.Getter;

@Getter
@MappedSuperclass
public abstract class UuidEntity extends BaseEntity {

    @Column(nullable = false, length = 36, unique = true)
    private String uuid;

    @PrePersist
    private void onCreate() {
        this.uuid = UUID.randomUUID().toString();
    }

}