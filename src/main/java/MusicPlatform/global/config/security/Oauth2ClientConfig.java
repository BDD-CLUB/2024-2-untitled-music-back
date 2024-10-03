package MusicPlatform.global.config.security;

import MusicPlatform.domain.oauth2.service.OAuth2UserService;
import MusicPlatform.global.handler.LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class Oauth2ClientConfig {

    private final OAuth2UserService oAuth2UserService;
    private final LoginSuccessHandler loginSuccessHandler;

    @Bean
    SecurityFilterChain securityFilterChane(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests ->
                requests.requestMatchers("/").permitAll()
        );

        http.csrf(AbstractHttpConfigurer::disable);

        http.oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfoEndpointConfig ->
                        userInfoEndpointConfig.userService(oAuth2UserService))
                .successHandler(loginSuccessHandler)
                .failureUrl("http://localhost:3000")
        );

        return http.build();
    }
}
