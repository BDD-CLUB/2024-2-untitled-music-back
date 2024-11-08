package MusicPlatform.domain.s3.controller;

import MusicPlatform.domain.s3.service.S3ImageService;
import MusicPlatform.domain.s3.service.S3MusicService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/upload")
public class S3Controller {

    private final S3ImageService s3ImageService;
    private final S3MusicService s3MusicService;

    @PostMapping("/images")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        String url = s3ImageService.uploadFile(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(url);
    }

    @PostMapping("/musics")
    public ResponseEntity<String> uploadFile(@RequestParam MultipartFile file) throws IOException {
        String url = s3MusicService.uploadFile(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(url);
    }
}
