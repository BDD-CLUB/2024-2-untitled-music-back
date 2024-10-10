package MusicPlatform.global.handler;

import MusicPlatform.domain.oauth2.adapter.AuthenticationAdapter;
import MusicPlatform.domain.oauth2.service.CookieService;
import MusicPlatform.global.provider.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final CookieService cookieService;
    private final String authServer = "http://localhost:3000";


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        AuthenticationAdapter authenticationAdapter = (AuthenticationAdapter) authentication.getPrincipal();

        String uuid = authenticationAdapter.getUuid();

        cookieService.authenticate(uuid, response);

        String url = UriComponentsBuilder.fromUriString(authServer)
                .path("")
                .build()
                .toUriString();

        response.sendRedirect(url);
    }
}
