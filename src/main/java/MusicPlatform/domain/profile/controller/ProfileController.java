package MusicPlatform.domain.profile.controller;

import MusicPlatform.domain.profile.service.ProfileService;
import MusicPlatform.domain.profile.service.dto.request.ProfileRequestDto;
import MusicPlatform.domain.profile.service.dto.response.ProfileResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
@Tag(name = "프로필 (Profile)")
public class ProfileController {

    private final ProfileService profileService;

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @Operation(summary = "프로필 생성")
    @PostMapping
    public ResponseEntity<Void> createProfile(@RequestBody @Valid ProfileRequestDto requestDto) {
        profileService.save(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @Operation(summary = "현재 선택된 프로필 조회")
    @GetMapping
    public ResponseEntity<ProfileResponseDto> getMyProfile(
            @CookieValue(value = "profile", defaultValue = "") String profileUuid, HttpServletResponse response) {
        ProfileResponseDto responseDto = profileService.get(profileUuid, response);
        return ResponseEntity.ok(responseDto);
    }

    // 특정 프로필 조회
    // 프로필 목록 조회
    // 프로필 수정
    // 프로필 삭제

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @Operation(summary = "현재 선택된 프로필 변경")
    @PostMapping("/{uuid}")
    public ResponseEntity<ProfileResponseDto> changeProfile(@PathVariable String uuid, HttpServletResponse response) {
        ProfileResponseDto responseDto = profileService.change(uuid, response);
        return ResponseEntity.ok(responseDto);
    }
}
