package MusicPlatform.domain.playlist.controller;

import MusicPlatform.domain.playlist._item.service.dto.request.PlaylistItemUpdateRequestDto;
import MusicPlatform.domain.playlist.service.PlaylistService;
import MusicPlatform.domain.playlist.service.dto.request.PlaylistRequestDto;
import MusicPlatform.domain.playlist.service.dto.response.PlaylistFullResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/playlists")
@Tag(name = "플레이리스트 (Playlist)")
public class PlaylistController {
    private final PlaylistService playlistService;

    //생성
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @Operation(summary = "플레이리스트 생성")
    @PostMapping
    public ResponseEntity<Void> createPlaylist(@RequestBody @Valid PlaylistRequestDto requestDto) {
        playlistService.save(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //수정
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @Operation(summary = "플레이리스트 수정")
    @PutMapping("/{uuid}")
    public ResponseEntity<Void> updatePlaylist(@AuthenticationPrincipal String artistUuid,
                                               @PathVariable String uuid,
                                               @RequestBody @Valid PlaylistRequestDto requestDto) {
        playlistService.update(artistUuid, uuid, requestDto);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @Operation(summary = "플레이리스트 커버 이미지 수정")
    @PutMapping("/{uuid}/cover-image")
    public ResponseEntity<Void> updatePlaylistCoverImage(@AuthenticationPrincipal String artistUuid,
                                                         @PathVariable String uuid,
                                                         String coverImageLink) {
        playlistService.updateCoverImage(artistUuid, uuid, coverImageLink);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @Operation(summary = "플레이리스트 내 트랙 수정 및 삭제")
    @PutMapping("/{uuid}/tracks")
    public ResponseEntity<Void> updatePlaylistTrack(@AuthenticationPrincipal String artistUuid,
                                                    @PathVariable String uuid,
                                                    @RequestBody @Valid PlaylistItemUpdateRequestDto requestDto) {
        playlistService.update(artistUuid, uuid, requestDto);
        return ResponseEntity.noContent().build();
    }

    //조회
    @Operation(summary = "플레이리스트 조회")
    @GetMapping("/{uuid}")
    public ResponseEntity<PlaylistFullResponseDto> getPlaylist(
            @PathVariable String uuid,
            @RequestParam(value = "itemPage", required = false, defaultValue = "0") int pageNo,
            @RequestParam(value = "itemPageSize", required = false, defaultValue = "10") int pageSize) {
        PlaylistFullResponseDto responseDto = playlistService.getPlaylist(uuid, pageNo, pageSize);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "플레이리스트 목록 조회")
    @GetMapping
    public ResponseEntity<List<PlaylistFullResponseDto>> getAll(
            @RequestParam(value = "page", required = false, defaultValue = "0") int pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        List<PlaylistFullResponseDto> responseDto = playlistService.getAll(pageNo, pageSize);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "특정 아티스트의 플레이리스트 목록 조회")
    @GetMapping("/artists/{uuid}/playlists")
    public ResponseEntity<List<PlaylistFullResponseDto>> getAllByArtist(@PathVariable String uuid) {
        List<PlaylistFullResponseDto> responseDto = playlistService.getAllByArtist(uuid);
        return ResponseEntity.ok(responseDto);
    }

    //삭제
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @Operation(summary = "플레이리스트 삭제")
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deletePlaylist(@AuthenticationPrincipal String artistUuid, @PathVariable String uuid) {
        playlistService.deletePlaylist(artistUuid, uuid);
        return ResponseEntity.noContent().build();
    }
}
