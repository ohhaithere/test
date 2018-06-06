/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.resource;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vtb.carrent.car.domain.model.KeyValuePair;
import ru.vtb.carrent.car.dto.CarDto;
import ru.vtb.carrent.car.service.CarService;
import ru.vtb.carrent.car.util.FilterUtils;
import ru.vtb.carrent.car.util.mapper.CarMapper;

import java.io.IOException;
import java.util.Date;
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
    @PreAuthorize("hasPermission('ru.vtb.carrent.car.resource.CarResource.createCar')")
    public CarDto createCar(@RequestBody CarDto carDto) {
        return mapper.toDto(service.create(mapper.fromDto(carDto)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PreAuthorize("hasPermission('ru.vtb.carrent.car.resource.CarResource.getCar')")
    public CarDto getCar(@PathVariable("id") Long id) {
        return mapper.toDto(service.find(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PreAuthorize("hasPermission('ru.vtb.carrent.car.resource.CarResource.getCars')")
    public Page<CarDto> getCars(String filter, Pageable pageable) {
        List<KeyValuePair> filterList = null;
        if (!StringUtils.isEmpty(filter)) {
            try {
                filterList = FilterUtils.getFilterList(filter);
                log.debug("Filter List: {}", filterList);
            } catch (IOException e) {
                log.error("filter ({}) could be parsed.", filter, e);
            }
        }

        return filterList == null || filterList.isEmpty()
                ? service.findPaginated(pageable).map(mapper::toDto)
                : service.getByFilter(filterList, pageable).map(mapper::toDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PreAuthorize("hasPermission('ru.vtb.carrent.car.resource.CarResource.updateCar')")
    public CarDto updateCar(@PathVariable("id") Long id,
                            @RequestBody CarDto carDto) {
        return mapper.toDto(service.update(mapper.fromDto(carDto.setId(id))));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PreAuthorize("hasPermission('ru.vtb.carrent.car.resource.CarResource.inRentCar')")
    public CarDto inRentCar(@PathVariable("id") Long id,
                            @RequestParam("endDate")
                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date endDate) {
        return mapper.toDto(service.inRent(id, endDate));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PreAuthorize("hasPermission('ru.vtb.carrent.car.resource.CarResource.inStockCar')")
    public CarDto inStockCar(@PathVariable("id") Long id) {
        return mapper.toDto(service.inStock(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PreAuthorize("hasPermission('ru.vtb.carrent.car.resource.CarResource.onMaintenance')")
    public CarDto onMaintenance(@PathVariable("id") Long id) {
        return mapper.toDto(service.onMaintenance(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PreAuthorize("hasPermission('ru.vtb.carrent.car.resource.CarResource.dropOut')")
    public CarDto dropOut(@PathVariable("id") Long id) {
        return mapper.toDto(service.dropOut(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PreAuthorize("hasPermission('ru.vtb.carrent.car.resource.CarResource.deleteCar')")
    public void deleteCar(@PathVariable("id") Long id) {
        service.delete(id);
    }

}
