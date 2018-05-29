/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.config.util.mapper;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import ru.vtb.carrent.car.config.BaseTestConfig;
import ru.vtb.carrent.car.util.mapper.CarMapper;

/**

 */
@TestConfiguration
public class CarMapperTestConfig extends BaseTestConfig {

    @Bean
    public CarMapper carRequestMapper() {
        return new CarMapper(mapperFacade);
    }

}
