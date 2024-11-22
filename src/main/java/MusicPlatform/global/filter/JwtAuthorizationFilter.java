package MusicPlatform.global.filter;

import MusicPlatform.global.provider.JwtProvider;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.replace("Bearer ", "");
        Claims claims = jwtProvider.validateToken(token);

        String uuid = claims.getSubject();
        List<?> rawAuthority = (List<?>) claims.get("authority");
        List<? extends GrantedAuthority> authorities = rawAuthority.stream()
                .map(auth -> new SimpleGrantedAuthority(auth.toString()))
                .toList();
        Authentication authentication = new UsernamePasswordAuthenticationToken(uuid, null, authorities);
        log.info("authentication uuid = " + authentication.getPrincipal().toString());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response); //다음 필터로 전달
    }
}
