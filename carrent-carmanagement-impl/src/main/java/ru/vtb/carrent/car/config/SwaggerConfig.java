/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.PropertyResolver;
import org.springframework.util.StringUtils;
import ru.vtb.dbo.annotations.swagger.EnableSwaggerDocumentation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.time.LocalDate;
import java.util.Collections;

/**
 * Swagger configuration. Disabled for tests.
 *
 * @author Valiantsin_Charkashy
 */
@Profile("!test")
@EnableSwaggerDocumentation
@Configuration
public class SwaggerConfig {

    private PropertyResolver propertyResolver;

    public SwaggerConfig(PropertyResolver propertyResolver) {
        this.propertyResolver = propertyResolver;
    }

    @Bean
    public Docket allApiDocumentation() {
        return (new Docket(DocumentationType.SWAGGER_2)).groupName("all").select()
                .apis(RequestHandlerSelectors.basePackage("ru.vtb.carrent.car.resource"))
                .build().apiInfo(this.buildApiInfo()).securitySchemes(Collections.singletonList(this.apiKey()));
    }

    private ApiKey apiKey() {
        return new ApiKey("Authorization", "api_key", "header");
    }

    private ApiInfo buildApiInfo() {
        String year = this.propertyResolver.getProperty("app.api.year");
        if (StringUtils.isEmpty(year)) {
            year = Integer.toString(LocalDate.now().getYear());
        }

        String description = this.propertyResolver.getProperty("app.api.description");
        if (StringUtils.isEmpty(description)) {
            description = "All API";
        }

        return (new ApiInfoBuilder()).title(this.propertyResolver.getProperty("spring.application.name")).description(description).version(this.propertyResolver.getProperty("app.api.version")).license(String.format("© %s EPAM Systems, Inc. All Rights Reserved.", year)).licenseUrl("#").build();
    }

}
