package ru.kk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private static final Contact CONTACT = new Contact(
            "Ходьков Михаил Владимировчи",
            "happymike19@gmail.com",
            "happymike19@gmail.com");

    @Bean
    public Docket allControllers(Environment env) {
        final Set<String> schemes = new HashSet<>(2);
        schemes.add("http");
        schemes.add("https");

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("api")
                .useDefaultResponseMessages(false)
                .protocols(schemes)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(buildApiInfo(env));
    }

    private ApiInfo buildApiInfo(Environment env) {
        return new ApiInfo(
                env.getProperty("spring.application.name", "short-url-api"),
                "Шаблон Spring-MVC",
                getClass().getPackage().getImplementationVersion(),
                null,
                CONTACT,
                null,
                null,
                Collections.emptyList());
    }

}
