package MusicPlatform.domain.follow.controller;

import MusicPlatform.domain.follow.service.FollowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping
@Tag(name = "팔로잉 (Follow)")
public class FollowController {
    private final FollowService followService;

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @Operation(summary = "팔로우")
    @PostMapping("artists/{uuid}/follow")
    public ResponseEntity<Void> follow(@AuthenticationPrincipal String myUuid,
                                       @PathVariable String uuid) {
        followService.save(myUuid, uuid);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @Operation(summary = "언팔로우")
    @DeleteMapping("/follow/{uuid}")
    public ResponseEntity<Void> unfollow(@AuthenticationPrincipal String myUuid,
                                       @PathVariable String uuid) {
        followService.delete(myUuid, uuid);
        return ResponseEntity.noContent().build();
    }
}
