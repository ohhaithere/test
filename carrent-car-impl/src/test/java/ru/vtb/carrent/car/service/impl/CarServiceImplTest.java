/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.service.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
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
import ru.vtb.carrent.car.config.BaseTestConfig;
import ru.vtb.carrent.car.config.util.mapper.CarMapperTestConfig;
import ru.vtb.carrent.car.domain.entity.Car;
import ru.vtb.carrent.car.exception.EntityNotFoundException;
import ru.vtb.carrent.car.repository.CarRepository;
import ru.vtb.carrent.car.service.CarService;


/**
 * Unit tests for {@link CarServiceImpl}.
 *
 */
@ActiveProfiles("test")
@ContextConfiguration(classes = CarMapperTestConfig.class)
public class CarServiceImplTest extends AbstractTestNGSpringContextTests {

    @Mock
    private CarRepository repository;

    private CarService service;
    private Long testId = 123L;
    private Car request;

    @BeforeTest
    public void init() {
        MockitoAnnotations.initMocks(this);
        service = new CarServiceImpl(repository);
    }

    @BeforeMethod
    public void reset() {
        Mockito.reset(repository);
        request = new Car().setId(testId);
    }

    @Test
    public void testCreate() throws Exception {
        when(repository.save(any(Car.class))).thenReturn(request);

        service.create(request);

        verify(repository).save(eq(request));
    }

    @Test
    public void testFind() {
        when(repository.findOne(anyLong())).thenReturn(request);

        Car found = service.find(testId);

        assertEquals(testId, found.getId());
        verify(repository).findOne(eq(testId));
    }

    @Test
    public void testUpdate() {
        when(repository.save(any(Car.class))).thenReturn(request);
        when(repository.findOne(anyLong())).thenReturn(request);

        service.update(request);

        verify(repository).save(eq(request));
        verify(repository).findOne(anyLong());
    }

    @Test(enabled = false)
    public void testDelete() {
        doNothing().when(repository).delete(anyLong());
        service.delete(testId);
        verify(repository).delete(eq(testId));
    }

    @Test(expectedExceptions = EntityNotFoundException.class)
    public void testFindWrongId() {
        when(repository.findOne(anyLong())).thenReturn(null);
        service.find(testId);
    }

}