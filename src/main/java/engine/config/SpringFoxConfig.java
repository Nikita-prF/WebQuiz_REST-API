package engine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Class of Swagger framework settings
 *
 */
@Configuration
@EnableSwagger2
public class SpringFoxConfig {


    /**
     * Method of generating API documentation
     *
     * @return API documentation
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("engine"))
                .paths(PathSelectors.any())
                .build().apiInfo(apiInfo());
    }

    /**
     * Method of generation of user API additional info
     *
     * @return User API info
     */
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Web-quiz REST API",
                "Hello! This is my first REST API project\n" +
                        "This engine implements a web quiz service with some available features.\n" +
                        "The project is based on 'Spring boot' and 'Hibernate' with 'H2' databases\n" +
                        " and this 'Swagger' will make it easier to understand all available quiz requests.",
                "ver. 1.0.0",
                "API",
                "Nikita Filimonov -- (https://github.com/Nikita-prF)",
                "",
                ""
        );
    }
}
