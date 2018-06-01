/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.resource;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vtb.carrent.car.domain.model.KeyValuePair;
import ru.vtb.carrent.car.dto.CarDto;
import ru.vtb.carrent.car.filter.FilteredPageRequest;
import ru.vtb.carrent.car.service.CarService;
import ru.vtb.carrent.car.util.FilterUtils;
import ru.vtb.carrent.car.util.mapper.CarMapper;

import java.util.Date;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Car resource implementation.
 *
 * @author Valiantsin_Charkashy
 */
@Slf4j
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
    public Page<CarDto> getCars(String filter, Pageable pageable) {
        List<KeyValuePair> filterList = null;
        if (!StringUtils.isEmpty(filter)) {
            try {
                filterList = FilterUtils.getFilterList(filter);
                log.debug(filterList.toString());
            } catch (IOException e) {
                log.error(String.format("filter (%s) could be parsed.", filter), e);
            }
        }

        return filterList == null || filterList.isEmpty()
                ? service.findPaginated(pageable).map(mapper::toDto)
                : service.getByFilter(filterList,  pageable).map(mapper::toDto);
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
    @ApiOperation(
            value = "Put a car with specific ID in rent."
    )
    public CarDto inRentCar(@ApiParam(value = "Car id", required = true)
                            @PathVariable("id") Long id,
                            @ApiParam(value = "End Date of rent", required = true)
                            @RequestParam("endDate")
                            @DateTimeFormat(pattern = "yyyy-MM-dd")
                                    Date endDate) {
        return mapper.toDto(service.inRent(id, endDate));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @ApiOperation(
            value = "Put a car with specific ID in stock."
    )
    public CarDto inStockCar(@ApiParam(value = "Car id", required = true)
                             @PathVariable("id") Long id) {
        return mapper.toDto(service.inStock(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @ApiOperation(
            value = "Put a car with specific ID on maintenance."
    )
    public CarDto onMaintenance(@ApiParam(value = "Car id", required = true)
                                @PathVariable("id") Long id) {
        return mapper.toDto(service.onMaintenance(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @ApiOperation(
            value = "Drop a car with specific ID."
    )
    public CarDto dropOut(@ApiParam(value = "Car id", required = true)
                          @PathVariable("id") Long id) {
        return mapper.toDto(service.dropOut(id));
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
