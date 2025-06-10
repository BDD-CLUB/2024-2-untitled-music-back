package MusicPlatform.domain.aws.controller;

import MusicPlatform.domain.aws.service.S3ImageService;
import MusicPlatform.domain.aws.service.S3MusicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/uploads")
@PreAuthorize("hasAnyAuthority('ROLE_USER')")
@Tag(name = "S3 업로드 (S3)")
public class S3Controller {

    private final S3ImageService s3ImageService;
    private final S3MusicService s3MusicService;

    @Deprecated
    @Operation(summary = "S3 이미지 업로드")
    @PostMapping("/s3images")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        String url = s3ImageService.uploadFile(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(url);
    }

    @Operation(summary = "S3 음악 업로드")
    @PostMapping("/musics")
    public ResponseEntity<String> uploadFile(@RequestParam MultipartFile file) throws IOException {
        String url = s3MusicService.uploadFile(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(url);
    }
}
