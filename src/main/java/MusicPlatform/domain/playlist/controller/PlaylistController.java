package MusicPlatform.domain.playlist.controller;

import MusicPlatform.domain.playlist._item.service.dto.request.PlaylistItemUpdateRequestDto;
import MusicPlatform.domain.playlist.service.PlaylistService;
import MusicPlatform.domain.playlist.service.dto.request.PlaylistRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    @Operation(summary = "플레이리스트 수정")
    @PutMapping("/{uuid}")
    public ResponseEntity<Void> updatePlaylist(@PathVariable String uuid,
                                               @RequestBody @Valid PlaylistRequestDto requestDto) {
        playlistService.update(uuid, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "플레이리스트 내 트랙 수정 및 삭제")
    @PutMapping("/{uuid}/tracks")
    public ResponseEntity<Void> updatePlaylistTrack(@PathVariable String uuid,
                                               @RequestBody @Valid PlaylistItemUpdateRequestDto requestDto) {
        playlistService.update(uuid, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //조회
    

    //삭제
}
