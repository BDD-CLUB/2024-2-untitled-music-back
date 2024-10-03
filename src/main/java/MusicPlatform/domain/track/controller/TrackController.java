package MusicPlatform.domain.track.controller;

import MusicPlatform.domain.track.repository.dto.request.TrackRequestDto;
import MusicPlatform.domain.track.repository.dto.request.TrackUpdateRequestDto;
import MusicPlatform.domain.track.repository.dto.response.TrackResponseDto;
import MusicPlatform.domain.track.service.TrackService;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;


import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class TrackController {

    private final TrackService trackService;

    @PostMapping("album/{uuid}/track")
    public ResponseEntity<Void> uploadTrack(@RequestPart @Valid TrackRequestDto requestDto,
                                            @RequestPart(required = false) final MultipartFile file,
                                            @PathVariable String uuid) {
        trackService.save(requestDto, file, uuid);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/track/{uuid}")
    public ResponseEntity<TrackResponseDto> getByUuid(@PathVariable String uuid) {
        TrackResponseDto responseDto = trackService.getTrack(uuid);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/artist/{uuid}/track")
    public ResponseEntity<List<TrackResponseDto>> getAllByArtist(@PathVariable String uuid) {
        List<TrackResponseDto> responseDto = trackService.getAllByArtist(uuid);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/track/{uuid}")
    public ResponseEntity<Void> updateByUuid(@RequestBody @Valid TrackUpdateRequestDto requestDto,
                                             @PathVariable String uuid) {
        trackService.updateByUuid(requestDto, uuid);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/track/{uuid}")
    public ResponseEntity<Void> deleteByUuid(@PathVariable String uuid) {
        trackService.deleteByUuid(uuid);
        return ResponseEntity.noContent().build();
    }
}
