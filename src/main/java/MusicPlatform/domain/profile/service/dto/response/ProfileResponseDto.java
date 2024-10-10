package MusicPlatform.domain.profile.service.dto.response;

import MusicPlatform.domain.profile.entity.Profile;
import lombok.Builder;

@Builder
public record ProfileResponseDto(
        String name,
        String profileImage,
        Boolean isMain
) {
    public static ProfileResponseDto from(Profile profile) {
        return ProfileResponseDto.builder()
                .name(profile.getName())
                .profileImage(profile.getProfileImage())
                .isMain(profile.isMain())
                .build();
    }
}
