package MusicPlatform.domain.oauth2.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CookieService {
    // @Value("${cookie.domain}")
    // private String domain;
    @Value("${cookie.max-age}")
    private int maxAge;
    private final JwtService jwtService;

    public Cookie makeAccessTokenCookie(String token) {
        return this.makeCookie("access_token", token, maxAge);
    }

    public Cookie deleteAccessTokenCookie() {
        return this.makeCookie("access_token", null, 0);
    }

    @Deprecated
    public Cookie makeProfileCookie(String profileUuid) {
        return this.makeCookie("profile", profileUuid, maxAge);
    }

    private Cookie makeCookie(String key, String value, int maxAge) {
        Cookie cookie = new Cookie(key, value);
        cookie.setSecure(true); // HTTPS에서만 쿠키가 전송되도록 설정
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        //cookie.setDomain(domain); //특정 호스트에 대해서만 쿠키 부여
        cookie.setMaxAge(maxAge);

        return cookie;
    }

    public void authenticate(String uuid, List<? extends GrantedAuthority> role, HttpServletResponse response) {
        String accessToken = jwtService.createToken(uuid, role);
        response.addCookie(this.makeAccessTokenCookie(accessToken));
        log.info("access token 쿠키 저장 = " + accessToken);
    }

    @Deprecated
    public void saveProfileCookie(String uuid, HttpServletResponse response) {
        response.addCookie(this.makeProfileCookie(uuid));
        log.info("profile 쿠키 저장 = " + uuid);
    }
}
