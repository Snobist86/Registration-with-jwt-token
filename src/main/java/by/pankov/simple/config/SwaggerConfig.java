package by.pankov.simple.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  private static final ModelRef ERROR = new ModelRef("Error");

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SPRING_WEB)
        .apiInfo(apiInfo())
        .useDefaultResponseMessages(false)
        .globalResponseMessage(RequestMethod.GET,
            Arrays.asList(
                new ResponseMessageBuilder()
                    .code(500)
                    .message("Server error")
                    .responseModel(ERROR)
                    .build(),
                new ResponseMessageBuilder()
                    .code(400)
                    .message("Bad request – wrong usage of the API")
                    .responseModel(ERROR)
                    .build(),
                new ResponseMessageBuilder()
                    .code(401)
                    .message("No or invalid authentication")
                    .responseModel(ERROR)
                    .build(),
                new ResponseMessageBuilder()
                    .code(403)
                    .message("Not permitted to access for users role")
                    .responseModel(ERROR)
                    .build(),
                new ResponseMessageBuilder()
                    .code(404)
                    .message("Requested resource not available (anymore)")
                    .responseModel(ERROR)
                    .build()
            ))
        .select()
        // ignore all REST endpoints that Spring Boot ships
        .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
        .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .version("0.1")
        .title("In Action Project")
        .description("Basic API for project")
        .build();
  }

}
