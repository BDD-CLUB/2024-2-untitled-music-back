package MusicPlatform.domain.member.service.dto;

import lombok.Builder;

@Builder
public record MemberDto(
        String uuid,
        String nickName,
        String email,
        String profileImage
) {

}
