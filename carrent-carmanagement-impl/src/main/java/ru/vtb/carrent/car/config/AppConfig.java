/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.config;

import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.vtb.carrent.car.Application;

/**
 * Application's configuration (contains common parts).
 *
 * @author Valiantsin_Charkashy
 */
@Configuration
@ComponentScan(basePackageClasses = Application.class)
@EnableCircuitBreaker
public class AppConfig {
}
