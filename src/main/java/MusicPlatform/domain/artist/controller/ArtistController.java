package MusicPlatform.domain.artist.controller;

import MusicPlatform.domain.artist.service.ArtistService;
import MusicPlatform.domain.artist.service.dto.response.ArtistResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/artist")
public class ArtistController {

    private final ArtistService artistService;

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @Operation(summary = "현재 로그인한 회원의 정보 조회")
    @GetMapping
    public ResponseEntity<ArtistResponseDto> getMyInfo() {
        ArtistResponseDto responseDto = artistService.getMyInfo();
        return ResponseEntity.ok(responseDto);
    }

//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
//    @Operation(summary = "ADMIN 권환 확인")
//    @GetMapping("/admin")
//    public ResponseEntity<Void> isAdmin() {
//        return ResponseEntity.noContent().build();
//    }
}
