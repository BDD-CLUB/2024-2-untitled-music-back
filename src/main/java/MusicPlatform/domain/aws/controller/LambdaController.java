package MusicPlatform.domain.aws.controller;

import MusicPlatform.domain.aws.service.LambdaService;
import MusicPlatform.domain.aws.service.dto.response.LambdaResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/uploads")
@PreAuthorize("hasAnyAuthority('ROLE_USER')")
@Tag(name = "이미지 업로드 (lambda)")
public class LambdaController {

    private final LambdaService lambdaService;

    @Operation(summary = "S3 이미지 업로드 (용량 압축)")
    @PostMapping(value = "/images",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LambdaResponseDto> uploadImage(@RequestPart("file") MultipartFile file) throws IOException {
        LambdaResponseDto responseDto = lambdaService.uploadImage(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
