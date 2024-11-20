package MusicPlatform.global.provider;

import MusicPlatform.domain.artist.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.Instant;
import java.time.ZonedDateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    @Value("${jwt.expiration_time}")
    private long ACCESS_TOKEN_EXP_TIME;
    @Value("${jwt.secret}")
    private String SECRET;

    public String createToken(String uuid, Role role) {
        Claims claims = Jwts.claims();
        claims.put("role", role);
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime tokenValidity = now.plusSeconds(ACCESS_TOKEN_EXP_TIME);

        return Jwts.builder()
                .setSubject(uuid)
                .setClaims(claims)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(tokenValidity.toInstant()))
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();
    }
}
