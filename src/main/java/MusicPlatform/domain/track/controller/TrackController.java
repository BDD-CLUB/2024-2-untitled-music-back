package MusicPlatform.domain.track.controller;

import MusicPlatform.domain.album.entity.Album;
import MusicPlatform.domain.album.service.AlbumService;
import MusicPlatform.domain.track.repository.dto.request.TrackRequestDto;
import MusicPlatform.domain.track.repository.dto.request.TrackUpdateRequestDto;
import MusicPlatform.domain.track.repository.dto.response.TrackGetResponseDto;
import MusicPlatform.domain.track.repository.dto.response.TrackResponseDto;
import MusicPlatform.domain.track.service.TrackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import jakarta.validation.Valid;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "트랙 (Track)")
public class TrackController {

    private final TrackService trackService;
    private final AlbumService albumService;

    @Operation(summary = "트랙 업로드")
    @PostMapping(value = "/album/{uuid}/track")
    public ResponseEntity<Void> uploadTrack(@ModelAttribute @Valid TrackRequestDto requestDto,
                                            @PathVariable String uuid) {
        Album album = albumService.getByUuid(uuid);
        trackService.save(requestDto, album);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "트랙 조회")
    @GetMapping("/track/{uuid}")
    public ResponseEntity<TrackGetResponseDto> getByUuid(@PathVariable String uuid) {
        TrackGetResponseDto responseDto = trackService.getTrack(uuid);
        return ResponseEntity.ok(responseDto);
    }

    @Deprecated
    @Operation(summary = "특정 아티스트의 트랙 목록 조회")
    @GetMapping("/artist/{uuid}/track")
    public ResponseEntity<List<TrackGetResponseDto>> getAllByArtist(@PathVariable String uuid) {
        List<TrackGetResponseDto> responseDto = trackService.getAllByArtist(uuid);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "트랙 수정")
    @PatchMapping("/track/{uuid}")
    public ResponseEntity<Void> updateByUuid(@RequestBody @Valid TrackUpdateRequestDto requestDto,
                                             @PathVariable String uuid) {
        trackService.updateByUuid(requestDto, uuid);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "트랙 삭제")
    @DeleteMapping("/track/{uuid}")
    public ResponseEntity<Void> deleteByUuid(@PathVariable String uuid) {
        trackService.deleteByUuid(uuid);
        return ResponseEntity.noContent().build();
    }
}
