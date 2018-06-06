/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.util.mapper;

import static org.testng.Assert.assertEquals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import ru.vtb.carrent.car.config.util.mapper.CarMapperTestConfig;
import ru.vtb.carrent.car.domain.entity.Car;
import ru.vtb.carrent.car.dto.CarDto;
import uk.co.jemos.podam.api.PodamFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Car Mapper test.
 *
 */
@ActiveProfiles("test")
@ContextConfiguration(classes = CarMapperTestConfig.class)
public class CarMapperTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private PodamFactory podamFactory;

    @Autowired
    private CarMapper carMapper;

    @Test
    public void testToDto() throws Exception {
        Car request = podamFactory.manufacturePojo(Car.class);
        CarDto requestDto = carMapper.toDto(request);
        assertEquals(request.getId(), requestDto.getId());
        assertEquals(request.getModel(), requestDto.getModel());
        assertEquals(request.getMileage(), requestDto.getMileage());
    }

    @Test
    public void testFromDto() throws Exception {
        CarDto requestDto = podamFactory.manufacturePojo(CarDto.class);
        Car request = carMapper.fromDto(requestDto);
        assertEquals(requestDto.getId(), request.getId());
        assertEquals(requestDto.getModel(), request.getModel());
        assertEquals(requestDto.getMileage(), request.getMileage());
    }

    @Test
    public void testToDtoList() throws Exception {
        Car request = podamFactory.manufacturePojo(Car.class);
        List<CarDto> requestDtoList = carMapper.toDto(Arrays.asList(request));
        CarDto requestDto = requestDtoList.get(0);
        assertEquals(request.getId(), requestDto.getId());
        assertEquals(request.getModel(), requestDto.getModel());
        assertEquals(request.getMileage(), requestDto.getMileage());
    }

    @Test
    public void testFromDtoList() throws Exception {
        CarDto requestDto = podamFactory.manufacturePojo(CarDto.class);
        List<Car> requestList = carMapper.fromDto(Arrays.asList(requestDto));
        Car request = requestList.get(0);
        assertEquals(requestDto.getId(), request.getId());
        assertEquals(requestDto.getModel(), request.getModel());
        assertEquals(requestDto.getMileage(), request.getMileage());
    }
}
