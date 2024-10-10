package MusicPlatform.domain.oauth2.service;

import MusicPlatform.global.provider.JwtProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CookieService {
    @Value("${cookie.max-age}")
    private int maxAge;
    @Value("${cookie.domain}")
    private String domain;
    private final JwtProvider jwtProvider;

    public Cookie makeAccessTokenCookie(String token) {
        return this.makeCookie("access_token", token, maxAge);
    }

    public Cookie deleteAccessTokenCookie() {
        return this.makeCookie("access_token", null, 0);
    }

    private Cookie makeCookie(String key, String value, int maxAge) {
        Cookie cookie = new Cookie(key, value);
        //cookie.setSecure(true); // HTTPS에서만 쿠키가 전송되도록 설정
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setDomain(domain);
        cookie.setMaxAge(maxAge);
        return cookie;
    }

    public void authenticate(String uuid, HttpServletResponse response) {
        String accessToken = jwtProvider.createToken(uuid);
        response.addCookie(this.makeAccessTokenCookie(accessToken));
    }
}
