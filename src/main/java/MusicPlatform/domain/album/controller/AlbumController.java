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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "앨범 (Album)")
public class AlbumController {

    private final AlbumService albumService;

    @PostMapping("/album")
    @Operation(summary = "앨범 업로드")
    public ResponseEntity<Void> uploadAlbum(@ModelAttribute @Valid AlbumRequestDto requestDto) {
        albumService.save(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "엘범 조회")
    @GetMapping("/album/{uuid}")
    public ResponseEntity<AlbumGetResponseDto> getByUuid(@PathVariable String uuid) {
        AlbumGetResponseDto responseDto = albumService.getAlbum(uuid);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "특정 아티스트의 엘범 목록 조회")
    @GetMapping("/artist/{uuid}/album")
    public ResponseEntity<List<AlbumResponseDto>> getAllByArtist(@PathVariable String uuid) {
        List<AlbumResponseDto> responseDto = albumService.getAllByArtist(uuid);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "앨범 수정")
    @PatchMapping("/album/{uuid}")
    public ResponseEntity<Void> updateByUuid(@RequestBody @Valid AlbumUpdateRequestDto requestDto,
                                             @PathVariable String uuid) {
        albumService.updateByUuid(requestDto, uuid);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "트랙 삭제")
    @DeleteMapping("/album/{uuid}")
    public ResponseEntity<Void> deleteByUuid(@PathVariable String uuid) {
        albumService.deleteByUuid(uuid);
        return ResponseEntity.noContent().build();
    }
}
