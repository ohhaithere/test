/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.vtb.carrent.car.domain.entity.Car;
import ru.vtb.carrent.car.repository.CarRepository;

@TestConfiguration
@EntityScan(basePackageClasses = {Car.class})
@EnableJpaRepositories(basePackageClasses = CarRepository.class)
public class JpaTestConfig {
}
