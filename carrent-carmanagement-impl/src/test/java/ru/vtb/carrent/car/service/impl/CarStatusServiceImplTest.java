/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2018 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.service.impl;

import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.vtb.carrent.car.domain.entity.Car;
import ru.vtb.carrent.car.kafka.Sender;
import ru.vtb.carrent.car.service.CarService;
import ru.vtb.carrent.car.service.CarStatusService;
import ru.vtb.carrent.car.service.PreferencesService;

/**
 * CarStatusServiceImplTest.
 *
 * @author Nikita_Puzankov
 */
public class CarStatusServiceImplTest {
    private CarStatusService carStatusService;
    private Sender sender;
    private CarService carService;
    private PreferencesService preferenceService;

    @BeforeMethod
    public void setUp() {
        sender = Mockito.mock(Sender.class);
        carService = Mockito.mock(CarService.class);
        preferenceService = Mockito.mock(PreferencesService.class);
        carStatusService = new CarStatusServiceImpl(
                sender,
                carService,
                preferenceService
        );
    }

    @Test
    public void testPutOnMaintenance() {
        carStatusService.putOnMaintenance(
                new Car()
        );
    }

    @Test
    public void testRelease() {
        carStatusService.release(
                new Car()
        );
    }

    @Test
    public void testDrop() {
        carStatusService.drop(
                new Car()
        );
    }

    @Test
    public void testRent() {
        carStatusService.rent(new Car());
    }
}