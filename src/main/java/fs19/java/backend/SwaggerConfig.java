package fs19.java.backend;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        System.out.println("SwaggerConfig.customOpenAPI");
        return new OpenAPI()
                .info(new Info()
                .title("Project Management App API Docs")
                .version("0.1")
                .description("Below Docs helps to understand the Project Management App Documentation")
                .termsOfService("http://swagger.io/terms/")
                .license(new io.swagger.v3.oas.models.info.License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
