package MusicPlatform.domain.album.controller;

import MusicPlatform.domain.album.service.AlbumService;
import MusicPlatform.domain.album.service.dto.request.AlbumRequestDto;
import MusicPlatform.domain.album.service.dto.request.AlbumUpdateRequestDto;
import MusicPlatform.domain.album.service.dto.response.AlbumGetResponseDto;
import MusicPlatform.domain.album.service.dto.response.AlbumResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "앨범 (Album)")
public class AlbumController {

    private final AlbumService albumService;

    @Operation(summary = "앨범 업로드")
    @PostMapping("/albums")
    public ResponseEntity<Void> uploadAlbum(@RequestBody @Valid AlbumRequestDto requestDto,
                                            @CookieValue(value = "profile", defaultValue = "") String profileUuid) {
        albumService.save(requestDto, profileUuid);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "엘범 목록 조회")
    @GetMapping("/albums")
    public ResponseEntity<List<AlbumGetResponseDto>> getAll(
            @RequestParam(value = "page", required = false, defaultValue = "0") int pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        List<AlbumGetResponseDto> responseDto = albumService.getAll(pageNo, pageSize);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "엘범 조회")
    @GetMapping("/albums/{uuid}")
    public ResponseEntity<AlbumGetResponseDto> getByUuid(@PathVariable String uuid) {
        AlbumGetResponseDto responseDto = albumService.getAlbum(uuid);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "특정 아티스트의 엘범 목록 조회")
    @GetMapping("/artists/{uuid}/albums")
    public ResponseEntity<List<AlbumResponseDto>> getAllByArtist(@PathVariable String uuid) {
        List<AlbumResponseDto> responseDto = albumService.getAllByArtist(uuid);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "특정 프로필의 엘범 목록 조회")
    @GetMapping("/profiles/{uuid}/albums")
    public ResponseEntity<List<AlbumResponseDto>> getAllByProfile(@PathVariable String uuid) {
        List<AlbumResponseDto> responseDto = albumService.getAllByProfile(uuid);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "앨범 수정")
    @PatchMapping("/albums/{uuid}")
    public ResponseEntity<Void> updateByUuid(@RequestBody @Valid AlbumUpdateRequestDto requestDto,
                                             @PathVariable String uuid) {
        albumService.updateByUuid(requestDto, uuid);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "앨범 삭제")
    @DeleteMapping("/albums/{uuid}")
    public ResponseEntity<Void> deleteByUuid(@PathVariable String uuid) {
        albumService.deleteByUuid(uuid);
        return ResponseEntity.noContent().build();
    }
}
