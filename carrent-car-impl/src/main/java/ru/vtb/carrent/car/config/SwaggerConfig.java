/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ru.vtb.dbo.annotations.swagger.EnableSwaggerDocumentation;

/**
 * Swagger configuration. Disabled for tests.
 *
 * @author Valiantsin_Charkashy
 */
@Profile("!test")
@EnableSwaggerDocumentation
@Configuration
public class SwaggerConfig {
}
