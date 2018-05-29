/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.config;

import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Eureka configuration. Disabled for tests.
 *
 * @author Valiantsin_Charkashy
 */
@Configuration
@EnableEurekaClient
@Profile("!test")
public class EurekaConfig {
}
