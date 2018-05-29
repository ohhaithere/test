/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.config;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import uk.co.jemos.podam.api.DefaultClassInfoStrategy;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;


/**
 *
 */
@TestConfiguration
public class BaseTestConfig {

    protected MapperFacade mapperFacade = new DefaultMapperFactory.Builder().build().getMapperFacade();

    @Bean
    public PodamFactory podamFactory() {
        PodamFactoryImpl podamFactory = new PodamFactoryImpl();
        DefaultClassInfoStrategy strategy = DefaultClassInfoStrategy.getInstance();
        podamFactory.setClassStrategy(strategy);
        return podamFactory;
    }
}
