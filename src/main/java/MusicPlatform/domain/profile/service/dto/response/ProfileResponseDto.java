package MusicPlatform.domain.profile.service.dto.response;

import MusicPlatform.domain.profile.entity.Profile;
import lombok.Builder;

@Builder
public record ProfileResponseDto(
        String uuid,
        String name,
        String description,
        String link1,
        String link2,
        String profileImage,
        Boolean isMain
) {
    public static ProfileResponseDto from(Profile profile) {
        return ProfileResponseDto.builder()
                .uuid(profile.getUuid())
                .name(profile.getName())
                .description(profile.getDescription())
                .link1(profile.getLink1())
                .link2(profile.getLink2())
                .profileImage(profile.getProfileImage())
                .isMain(profile.isMain())
                .build();
    }
}
