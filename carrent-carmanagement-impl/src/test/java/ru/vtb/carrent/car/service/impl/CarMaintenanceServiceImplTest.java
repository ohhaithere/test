/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.service.impl;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.vtb.carrent.car.config.util.mapper.CarMapperTestConfig;
import ru.vtb.carrent.car.domain.entity.Car;
import ru.vtb.carrent.car.kafka.Sender;
import ru.vtb.carrent.car.repository.CarRepository;
import ru.vtb.carrent.car.status.Status;

import java.util.Collections;
import java.util.Date;

/**
 * Unit test for {@link CarMaintenanceServiceImpl}.
 *
 * @author Nikita_Puzankov
 */
@ActiveProfiles("test")
@ContextConfiguration(classes = CarMapperTestConfig.class)
public class CarMaintenanceServiceImplTest {

    @InjectMocks
    private CarMaintenanceServiceImpl carMaintenanceService;

    @Mock
    private CarRepository carRepository;

    @Mock
    private Sender sender;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCheckAndPutOnMaintenance() {
        Mockito.when(
                carRepository.findByCurrentStatusIgnoreCase(Status.IN_STOCK.getDisplayName())
        ).thenReturn(
                Collections.singletonList(
                        new Car(
                                1l,
                                "s500",
                                "a000aa",
                                new Date(),
                                1,
                                new Date(),
                                new Date(),
                                Status.IN_STOCK.getDisplayName(),
                                new Date(),
                                Status.IN_STOCK.getDisplayName(),
                                new Date(),
                                1L)
                )
        );
        carMaintenanceService.checkAndPutOnMaintenance();
    }
}