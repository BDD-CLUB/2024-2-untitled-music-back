package MusicPlatform.domain.artist.controller;

import MusicPlatform.domain.artist.service.ArtistService;
import MusicPlatform.domain.artist.service.dto.response.ArtistResponseDto;
import MusicPlatform.domain.s3.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/artist")
@Tag(name = "회원 (Artist)")
public class ArtistController {

    private final ArtistService artistService;
    private final S3Service s3Service;


    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @Operation(summary = "현재 로그인한 회원의 정보 조회")
    @GetMapping
    public ResponseEntity<ArtistResponseDto> getMyInfo() {
        ArtistResponseDto responseDto = artistService.getMyInfo();
        return ResponseEntity.ok(responseDto);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @Operation(summary = "artist의 image 변경 api")
    @PutMapping("/image-change")
    public ResponseEntity<?> changeArtistImage(@RequestParam("file") MultipartFile file) throws IOException {

        String url = s3Service.uploadFile(file);
        ArtistResponseDto artistResponseDto = artistService.changeArtistImage(url);
        return ResponseEntity.ok(artistResponseDto);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @Operation(summary = "artist 삭제 api")
    @DeleteMapping
    public ResponseEntity<?> deleteArtist() {
        artistService.removeArtist();
        return ResponseEntity.ok().build();
    }

}
