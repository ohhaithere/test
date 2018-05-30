/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.service.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.vtb.carrent.car.config.util.mapper.CarMapperTestConfig;
import ru.vtb.carrent.car.domain.entity.Car;
import ru.vtb.carrent.car.exception.EntityNotFoundException;
import ru.vtb.carrent.car.repository.CarRepository;
import ru.vtb.carrent.car.repository.impl.MemoryCarRepositoryStub;
import ru.vtb.carrent.car.service.CarService;


/**
 * Unit tests for {@link CarServiceImpl}.
 *
 */
@ActiveProfiles("test")
@ContextConfiguration(classes = CarMapperTestConfig.class)
public class CarServiceImplTest extends AbstractTestNGSpringContextTests {

    @Mock
//    private CarRepository repository;
    private MemoryCarRepositoryStub repository;

    private CarService service;
    private Long testCarId = 123L;
    private Car testCar;

    @BeforeTest
    public void init() {
        MockitoAnnotations.initMocks(this);
        service = new CarServiceImpl(repository);
    }

    @BeforeMethod
    public void reset() {
        Mockito.reset(repository);
        testCar = new Car().setId(testCarId);
    }

    @Test
    public void testCreate() {
        when(repository.save(any(Car.class))).thenReturn(testCar);

        service.create(testCar);

        verify(repository).save(eq(testCar));
    }

    @Test
    public void testFind() {
        when(repository.findOne(anyLong())).thenReturn(testCar);

        Car found = service.find(testCarId);

        assertEquals(testCarId, found.getId());
        verify(repository).findOne(eq(testCarId));
    }

    @Test(expectedExceptions = EntityNotFoundException.class)
    public void testFindWrongId() {
        when(repository.findOne(anyLong())).thenReturn(null);
        service.find(testCarId);
    }

    @Test
    public void testUpdate() {
        when(repository.exists(testCar.getId())).thenReturn(true);
        when(repository.save(any(Car.class))).thenReturn(testCar);

        service.update(testCar);

        verify(repository).save(eq(testCar));
        verify(repository).exists(anyLong());
    }

    @Test(expectedExceptions = EntityNotFoundException.class)
    public void testUpdateNonexistentCar() {
        when(repository.exists(testCar.getId())).thenReturn(false);

        service.update(testCar);
    }

    @Test(enabled = false)
    public void testDelete() {
        doNothing().when(repository).delete(anyLong());
        service.delete(testCarId);
        verify(repository).delete(eq(testCarId));
    }

}