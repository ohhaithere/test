/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.resource;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vtb.carrent.car.domain.entity.Car;
import ru.vtb.carrent.car.dto.CarDto;
import ru.vtb.carrent.car.exception.EntityNotFoundException;
import ru.vtb.carrent.car.service.CarService;
import ru.vtb.carrent.car.util.mapper.CarMapper;

import java.util.List;

/**
 * Car resource implementation.
 *
 * @author Valiantsin_Charkashy
 */
@RestController
public class CarResourceImpl implements CarResource {

    private final CarService service;
    private final CarMapper mapper;

    /**
     * Car controller constructor.
     *
     * @param service   service
     * @param carMapper mapper
     */
    @Autowired
    public CarResourceImpl(CarService service, CarMapper carMapper) {
        this.service = service;
        this.mapper = carMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @ApiOperation(value = "Create a car")
    public CarDto createCar(@ApiParam(value = "Car body", required = true) @RequestBody CarDto carDto) {
        return mapper.toDto(service.create(mapper.fromDto(carDto)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @ApiOperation("Get Car by ID")
    public CarDto getCar(@PathVariable("id") Long id) {
        return mapper.toDto(service.find(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<CarDto> getCars(Pageable pageRequest) {
        return service.findPaginated(pageRequest).map(mapper::toDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @ApiOperation(
            value = "Update a car with specific ID",
            notes = "Method provides validation errors")
    public CarDto updateCar(@ApiParam(value = "Car id", required = true)
                            @PathVariable("id") Long id,
                            @ApiParam(value = "Car body", required = true)
                            @RequestBody CarDto carDto) {
        return mapper.toDto(service.update(mapper.fromDto(carDto.setId(id))));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @ApiOperation(value = "Delete a Car")
    public void deleteCar(@PathVariable("id") Long id) {
        service.delete(id);
    }

}
