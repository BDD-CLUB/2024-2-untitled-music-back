package MusicPlatform.domain.aws.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LambdaRequestDto(
        @NotNull
        String filename,
        @NotBlank
        String base64Data,
        @NotBlank
        String bucket
) {
}
