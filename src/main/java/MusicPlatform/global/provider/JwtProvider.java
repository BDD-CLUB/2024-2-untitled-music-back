package MusicPlatform.global.provider;

import io.jsonwebtoken.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    @Value("${jwt.expiration_time}")
    private long ACCESS_TOKEN_EXP_TIME;
    @Value("${jwt.secret}")
    private String SECRET;

    public String createToken(String uuid, List<? extends GrantedAuthority> authorities) {
        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)  // 권한을 String으로 변환
                .toList();
        Map<String, Object> claims = new HashMap<>();
        claims.put("authority", roles);
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime tokenValidity = now.plusSeconds(ACCESS_TOKEN_EXP_TIME);

        return Jwts.builder()
                .setClaims(claims) // setClaims와 setSubject 순서 지킬것
                .setSubject(uuid)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(tokenValidity.toInstant()))
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();
    }
}
