package MusicPlatform.global.config.security;

import MusicPlatform.domain.oauth2.service.OAuth2UserService;
import MusicPlatform.global.handler.LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class Oauth2ClientConfig {

    private final OAuth2UserService oAuth2UserService;
    private final LoginSuccessHandler loginSuccessHandler;

    @Value(value = "${server.error-page}")
    private String errorPage;

    @Bean
    SecurityFilterChain securityFilterChane(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests ->
                requests.requestMatchers("/**", "/api-docs/**", "/swagger-ui/**").permitAll()
        );

        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable);

        http.oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfoEndpointConfig ->
                        userInfoEndpointConfig.userService(oAuth2UserService))
                .successHandler(loginSuccessHandler)
                .failureUrl(errorPage)
        );

        return http.build();
    }
}
