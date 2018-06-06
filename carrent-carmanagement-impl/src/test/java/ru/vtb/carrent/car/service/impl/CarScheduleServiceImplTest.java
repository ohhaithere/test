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
import ru.vtb.carrent.car.kafka.Sender;
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
    private Sender sender;

    @BeforeMethod
    public void setUp() {
        carRepository = Mockito.mock(CarRepository.class);
        stateMachineSupplier = Mockito.mock(StateMachineSupplier.class);
        sender = Mockito.mock(Sender.class);
        carMaintenanceService = new CarScheduleServiceImpl(carRepository, stateMachineSupplier, sender);
    }

    @Test
    public void testCheckAndRent() {
        Mockito.when(
                carRepository.findByCurrentStatusIgnoreCase(Status.IN_STOCK.name())
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
                                Status.IN_STOCK.name(),
                                new Date(),
                                Status.IN_STOCK.name(),
                                new Date(),
                                1L,
                                null)
                )
        );
        Mockito.when(stateMachineSupplier.getCarStateMachine(any()))
                .thenReturn(Mockito.mock(StateMachine.class));

        carMaintenanceService.checkAndRent();
    }

    @Test
    public void testCheckAndPutOnMaintenance() {
        Mockito.when(
                carRepository.findByCurrentStatusIgnoreCase(Status.IN_STOCK.name())
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
                                Status.IN_STOCK.name(),
                                new Date(),
                                Status.IN_STOCK.name(),
                                new Date(),
                                1L,
                                null)
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
                                Status.ON_MAINTENANCE.name(),
                                Status.IN_RENT.name()
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
                                Status.IN_RENT.name(),
                                new Date(),
                                Status.IN_STOCK.name(),
                                Date.from(ZonedDateTime.of(2018, 05, 20, 20, 20, 20, 20, ZoneId.systemDefault()).toInstant()),
                                1L,
                                null)
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
                                Status.ON_MAINTENANCE.name(),
                                Status.IN_RENT.name()
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
                                Status.ON_MAINTENANCE.name(),
                                new Date(),
                                Status.IN_STOCK.name(),
                                Date.from(ZonedDateTime.of(2018, 05, 20, 20, 20, 20, 20, ZoneId.systemDefault()).toInstant()),
                                1L,
                                null)
                )
        );
        Mockito.when(stateMachineSupplier.getCarStateMachine(any()))
                .thenReturn(Mockito.mock(StateMachine.class));

        carMaintenanceService.checkAndRelease();
    }
}