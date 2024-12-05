package MusicPlatform.domain.playlist.controller;

import MusicPlatform.domain.playlist.service.PlaylistService;
import MusicPlatform.domain.playlist.service.dto.request.PlaylistRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/playlist")
public class PlaylistController {
    private final PlaylistService playlistService;

    //생성
    @Operation(summary = "플레이리스트 생성")
    @PostMapping()
    public ResponseEntity<Void> createPlaylist(@RequestBody @Valid PlaylistRequestDto requestDto) {
        playlistService.save(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //수정
    //조회
    //삭제
}
