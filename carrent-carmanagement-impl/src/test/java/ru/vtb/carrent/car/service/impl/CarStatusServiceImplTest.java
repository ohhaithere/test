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

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;
import static ru.vtb.carrent.car.status.Status.IN_RENT;

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
    public void testReleaseAfterRent() {
        long rentTime = Calendar.getInstance().getTimeInMillis() - TimeUnit.HOURS.toMillis(1);
        Car car = new Car()
                .setId(1L)
                .setCurrentStatus(IN_RENT.name())
                .setDateOfCurrentStatus(new Date(rentTime))
                .setMileage(0);

        carStatusService.release(car, true);

        assertEquals(60, car.getMileage());
    }

    @Test
    public void testReleaseAfterService() {
        long rentTime = Calendar.getInstance().getTimeInMillis() - TimeUnit.HOURS.toMillis(1);
        Car car = new Car()
                .setId(1L)
                .setCurrentStatus(IN_RENT.name())
                .setDateOfCurrentStatus(new Date(rentTime))
                .setMileage(0);

        carStatusService.release(car, false);

        assertEquals(0, car.getMileage());
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