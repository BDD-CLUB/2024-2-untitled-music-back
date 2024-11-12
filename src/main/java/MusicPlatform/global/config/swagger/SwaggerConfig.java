package MusicPlatform.global.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Value("${server.api}")
    private String api;

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Untitled API Document")
                .version("v0.1.0-alpha");

        Server server = new Server();
        server.setUrl(api);

        return new OpenAPI()
                .components(new Components())
                .info(info)
                .servers(List.of(server));
    }

    @Bean
    public GroupedOpenApi allApi() {
        return GroupedOpenApi.builder()
                .group("all")
                .displayName("All API")
                .pathsToMatch("/**")
                .build();
    }
}
