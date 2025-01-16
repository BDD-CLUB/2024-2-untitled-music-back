package MusicPlatform.domain.artist.controller;

import MusicPlatform.domain.artist.service.ArtistService;
import MusicPlatform.domain.artist.service.dto.request.ArtistImageUrlDto;
import MusicPlatform.domain.artist.service.dto.response.ArtistResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/artists")
@Tag(name = "회원 (Artist)")
public class ArtistController {

    private final ArtistService artistService;

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @Operation(summary = "현재 로그인한 회원의 정보 조회")
    @GetMapping
    public ResponseEntity<ArtistResponseDto> getMyInfo() {
        ArtistResponseDto responseDto = artistService.getMyInfo();
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "특정 회원 정보 조회")
    @GetMapping("/{uuid}")
    public ResponseEntity<ArtistResponseDto> getArtist(@PathVariable String uuid) {
        ArtistResponseDto responseDto = artistService.getByUuid(uuid);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "회원 목록 조회")
    @GetMapping
    public ResponseEntity<List<ArtistResponseDto>> getAll(
            @RequestParam(value = "page", required = false, defaultValue = "0") int pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        List<ArtistResponseDto> responseDto = artistService.getAll(pageNo, pageSize);
        return ResponseEntity.ok(responseDto);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @Operation(summary = "회원의 프로필 이미지 변경")
    @PutMapping("/profile-image")
    public ResponseEntity<ArtistResponseDto> changeArtistImage(@RequestBody @Valid ArtistImageUrlDto artistImageUrlDto){
        ArtistResponseDto artistResponseDto = artistService.changeArtistImage(
                artistImageUrlDto.imageUrl()
        );
        return ResponseEntity.ok(artistResponseDto);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @Operation(summary = "회원 탈퇴")
    @DeleteMapping
    public ResponseEntity<?> deleteArtist() {
        artistService.removeArtist();
        return ResponseEntity.ok().build();
    }
}
