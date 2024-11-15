package MusicPlatform.domain.oauth2.service;

import MusicPlatform.domain.artist.entity.Role;
import MusicPlatform.global.provider.JwtProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
    private final JwtProvider jwtProvider;

    public Cookie makeAccessTokenCookie(String token) {
        return this.makeCookie("access_token", token, maxAge);
    }

    public Cookie deleteAccessTokenCookie() {
        return this.makeCookie("access_token", null, 0);
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

    public void authenticate(String uuid, Role role, HttpServletResponse response) {
        String accessToken = jwtProvider.createToken(uuid, role);
        response.addCookie(this.makeAccessTokenCookie(accessToken));
        
        log.info("쿠키 저장 = " + response.getHeader("Set-Cookie"));
    }
}
