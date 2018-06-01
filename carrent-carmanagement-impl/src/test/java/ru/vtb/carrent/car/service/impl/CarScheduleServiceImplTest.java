/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.service.impl;

import org.mockito.Mockito;
import org.springframework.statemachine.StateMachine;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.vtb.carrent.car.config.util.mapper.CarMapperTestConfig;
import ru.vtb.carrent.car.domain.entity.Car;
import ru.vtb.carrent.car.repository.CarRepository;
import ru.vtb.carrent.car.statemachine.StateMachineSupplier;
import ru.vtb.carrent.car.status.Status;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import static org.mockito.Matchers.any;

/**
 * Unit test for {@link CarScheduleServiceImpl}.
 *
 * @author Nikita_Puzankov
 */
@ActiveProfiles("test")
@ContextConfiguration(classes = CarMapperTestConfig.class)
public class CarScheduleServiceImplTest {

    private CarScheduleServiceImpl carMaintenanceService;

    private CarRepository carRepository;
    private StateMachineSupplier stateMachineSupplier;

    @BeforeMethod
    public void setUp() {
        carRepository = Mockito.mock(CarRepository.class);
        stateMachineSupplier = Mockito.mock(StateMachineSupplier.class);
        carMaintenanceService = new CarScheduleServiceImpl(carRepository, stateMachineSupplier);
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
        Mockito.when(stateMachineSupplier.getCarStateMachine(any()))
                .thenReturn(Mockito.mock(StateMachine.class));

        carMaintenanceService.checkAndPutOnMaintenance();
    }

    @Test
    public void testCheckAndReleaseFromRent() {
        Mockito.when(
                carRepository.findByCurrentStatusInIgnoreCase(
                        Arrays.asList(
                                Status.ON_MAINTENANCE.getDisplayName(),
                                Status.IN_RENT.getDisplayName()
                        )
                )
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
                                Status.IN_RENT.getDisplayName(),
                                new Date(),
                                Status.IN_STOCK.getDisplayName(),
                                Date.from(ZonedDateTime.of(2018, 05, 20, 20, 20, 20, 20, ZoneId.systemDefault()).toInstant()),
                                1L)
                )
        );
        Mockito.when(stateMachineSupplier.getCarStateMachine(any()))
                .thenReturn(Mockito.mock(StateMachine.class));

        carMaintenanceService.checkAndRelease();
    }


    @Test
    public void testCheckAndReleaseFromMaintenance() {
        Mockito.when(
                carRepository.findByCurrentStatusInIgnoreCase(
                        Arrays.asList(
                                Status.ON_MAINTENANCE.getDisplayName(),
                                Status.IN_RENT.getDisplayName()
                        )
                )
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
                                Status.ON_MAINTENANCE.getDisplayName(),
                                new Date(),
                                Status.IN_STOCK.getDisplayName(),
                                Date.from(ZonedDateTime.of(2018, 05, 20, 20, 20, 20, 20, ZoneId.systemDefault()).toInstant()),
                                1L)
                )
        );
        Mockito.when(stateMachineSupplier.getCarStateMachine(any()))
                .thenReturn(Mockito.mock(StateMachine.class));

        carMaintenanceService.checkAndRelease();
    }
}