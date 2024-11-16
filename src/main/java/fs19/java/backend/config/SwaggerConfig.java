package fs19.java.backend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Project Management App API Docs")
                        .version("0.1")
                        .description("Below Docs helps to understand the Project Management App Documentation")
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .components(new Components()
                        .addResponses("BadRequest", createResponse("400", "Invalid input data"))
                        .addResponses("NotFound", createResponse("404", "Resource not found"))
                        .addResponses("InternalError", createResponse("500", "Internal server error"))
                        .addResponses("Created", createResponse("201", "Resource created successfully"))
                        .addResponses("NoContent", createResponse("204", "No content")));
    }

    private ApiResponse createResponse(String code, String description) {
        return new ApiResponse()
                .description(description)
                .content(new Content().addMediaType("application/json",
                        new MediaType().schema(new Schema<>().type("string").example(description))));
    }
}
