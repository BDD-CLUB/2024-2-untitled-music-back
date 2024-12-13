package MusicPlatform.domain.profile.service.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record ProfileRequestDto(
        @NotEmpty
        @Size(max=25)
        String name,
        @Size(min=0, max=400)
        String description,
        String link1,
        String link2,
        String profileImage,
        boolean isMain
) {
}
