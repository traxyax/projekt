package sn.ashia.projekt.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({"dev", "pre-prod"})
@Configuration
public class DocumentationConfig {
    @Bean
    public GroupedOpenApi apiOpenApi() {
        String[] paths = {"/api/**"};
        return GroupedOpenApi.builder().group("api").pathsToMatch(paths).build();
    }

    @Bean
    public GroupedOpenApi userOpenApi() {
        return generateGroupedOpenApi("user", "users");
    }

    @Bean
    public GroupedOpenApi projectOpenApi() {
        return generateGroupedOpenApi("project", "projects");
    }

    @Bean
    public GroupedOpenApi projectRiskOpenApi() {
        return generateGroupedOpenApi("project-risk", "project-risks");
    }

    @Bean
    public GroupedOpenApi projectComponentOpenApi() {
        return generateGroupedOpenApi("project-component", "project-components");
    }

    @Bean
    public GroupedOpenApi componentActivityOpenApi() {
        return generateGroupedOpenApi("component-activity", "component-activities");
    }

    @Bean
    public OpenAPI projektOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Projekt API")
                        .description("Project management")
                );
    }

    private GroupedOpenApi generateGroupedOpenApi(String group, String path) {
        return GroupedOpenApi.builder()
                .group(group)
                .pathsToMatch("/api/" + path + "/**")
                .addOpenApiCustomizer(openApi -> openApi.info(new Info().title(group + " api")))
                .build();
    }
}
