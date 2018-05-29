/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.config.resource;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import ru.vtb.carrent.car.config.BaseTestConfig;
import ru.vtb.carrent.car.util.mapper.CarMapper;

/**
 * Car resource test config
 */
@TestConfiguration
public class CarResourceImplTestConfig extends BaseTestConfig {

    @Bean
    public CarMapper carMapper() {
        return new CarMapper(mapperFacade);
    }

}
