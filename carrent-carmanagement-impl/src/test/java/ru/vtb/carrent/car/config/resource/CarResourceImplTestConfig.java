/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2018 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.config.resource;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import ru.vtb.carrent.car.config.BaseTestConfig;
import ru.vtb.carrent.car.resource.SecurityContextHelper;
import ru.vtb.carrent.car.util.mapper.CarMapper;

import java.util.Arrays;
import java.util.List;

/**
 * Car resource test config.
 *
 * @author Valiantsin_Charkashy
 */
@TestConfiguration
public class CarResourceImplTestConfig extends BaseTestConfig {

    @Bean
    public CarMapper carMapper() {
        return new CarMapper(mapperFacade);
    }

    @Bean
    public SecurityContextHelper securityContextHelper() {
        return new SecurityContextHelper() {
            @Override
            public List<String> getRoles() {
                return Arrays.asList("Администратор");
            }

            @Override
            public Long getLocationId() {
                return 0L;
            }
        };
    }

}
