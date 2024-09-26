package MusicPlatform.domain.track.controller;

import MusicPlatform.domain.track.repository.dto.request.TrackRequestDto;
import MusicPlatform.domain.track.repository.dto.response.TrackResponseDto;
import MusicPlatform.domain.track.service.TrackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;


import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class TrackController {
    /**
     *  트랙 업로드
     *  트랙 조회
     *  특정 회원의 트랙 목록 조회
     *  트랙 수정 (이후 인가 추가)
     *  트랙 삭제 (이후 인가 추가)
     */

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
}
