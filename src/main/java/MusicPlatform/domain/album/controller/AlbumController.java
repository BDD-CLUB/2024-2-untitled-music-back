package MusicPlatform.domain.album.controller;

import MusicPlatform.domain.album.service.AlbumService;
import MusicPlatform.domain.album.service.dto.request.AlbumRequestDto;
import MusicPlatform.domain.album.service.dto.request.AlbumUpdateRequestDto;
import MusicPlatform.domain.album.service.dto.response.AlbumFullResponseDto;
import MusicPlatform.domain.album.service.dto.response.AlbumBasicResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
                                            @AuthenticationPrincipal String artistUuid) {
        albumService.save(requestDto, artistUuid);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "엘범 목록 조회")
    @GetMapping("/albums")
    public ResponseEntity<List<AlbumFullResponseDto>> getAll(
            @RequestParam(value = "page", required = false, defaultValue = "0") int pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        List<AlbumFullResponseDto> responseDto = albumService.getAll(pageNo, pageSize);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "엘범 조회")
    @GetMapping("/albums/{uuid}")
    public ResponseEntity<AlbumFullResponseDto> getByUuid(
            @PathVariable String uuid,
            @RequestParam(value = "trackPage", required = false, defaultValue = "0") int pageNo,
            @RequestParam(value = "trackPageSize", required = false, defaultValue = "10") int pageSize) {
        AlbumFullResponseDto responseDto = albumService.getAlbum(uuid, pageNo, pageSize);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "특정 아티스트의 엘범 목록 조회")
    @GetMapping("/artists/{uuid}/albums")
    public ResponseEntity<List<AlbumBasicResponseDto>> getAllByArtist(
            @PathVariable String uuid,
            @RequestParam(value = "page", required = false, defaultValue = "0") int pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize) {
        List<AlbumBasicResponseDto> responseDto = albumService.getAllByArtist(uuid, pageNo, pageSize);
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
