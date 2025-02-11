package MusicPlatform.domain.track.controller;

import MusicPlatform.domain.track.service.dto.request.TrackRequestDto;
import MusicPlatform.domain.track.service.dto.request.TrackUpdateRequestDto;
import MusicPlatform.domain.track.service.dto.response.TrackFullResponseDto;
import MusicPlatform.domain.track.service.TrackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "트랙 (Track)")
public class TrackController {

    private final TrackService trackService;

    @Operation(summary = "트랙 업로드")
    @PostMapping(value = "/albums/{uuid}/tracks")
    public ResponseEntity<Void> uploadTrack(@RequestBody @Valid TrackRequestDto requestDto,
                                            @PathVariable String uuid) {
        trackService.save(requestDto, uuid);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "트랙 조회")
    @GetMapping("/tracks/{uuid}")
    public ResponseEntity<TrackFullResponseDto> getTrack(@PathVariable String uuid) {
        TrackFullResponseDto responseDto = trackService.getByUuid(uuid);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "트랙 목록 조회")
    @GetMapping("/tracks")
    public ResponseEntity<List<TrackFullResponseDto>> getAll(
            @RequestParam(value = "page", required = false, defaultValue = "0") int pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(value = "direction", required = false, defaultValue = "desc") String direction) {
        List<TrackFullResponseDto> responseDto = trackService.getAll(pageNo, pageSize, sortBy, direction);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "특정 아티스트의 트랙 목록 조회")
    @GetMapping("/artists/{uuid}/tracks")
    public ResponseEntity<List<TrackFullResponseDto>> getAllByArtist(
            @RequestParam(value = "page", required = false, defaultValue = "0") int pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(value = "direction", required = false, defaultValue = "desc") String direction,
            @PathVariable String uuid) {
        List<TrackFullResponseDto> responseDto = trackService.getAllByArtist(uuid, pageNo, pageSize, sortBy, direction);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "트랙 수정")
    @PatchMapping("/tracks/{uuid}")
    public ResponseEntity<Void> updateTrack(@RequestBody @Valid TrackUpdateRequestDto requestDto,
                                            @PathVariable String uuid) {
        trackService.updateByUuid(requestDto, uuid);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "트랙 삭제")
    @DeleteMapping("/tracks/{uuid}")
    public ResponseEntity<Void> deleteTrack(@PathVariable String uuid) {
        trackService.deleteByUuid(uuid);
        return ResponseEntity.noContent().build();
    }
}
