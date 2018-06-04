/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2018 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.resource;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.vtb.carrent.car.dto.CarDto;

import java.util.Date;

/**
 * Car resource interface.
 *
 * @author Valiantsin_Charkashy
 */
@RequestMapping("/ui/car")
public interface CarResource {

    /**
     * Creates car.
     *
     * @param carDto car
     * @return created car
     */
    @ApiOperation(value = "Create a car")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @PreAuthorize("hasPermission('ru.vtb.carrent.car.resource.CarResource.createCar')")
    CarDto createCar(
            @ApiParam(value = "Car body", required = true)
            @RequestBody CarDto carDto);

    /**
     * Gets Car by id.
     *
     * @param id car id
     * @return found car
     */
    @ApiOperation("Get Car by ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasPermission('ru.vtb.carrent.car.resource.CarResource.getCar')")
    CarDto getCar(@PathVariable("id") Long id);

    /**
     * Get paginated cars by filter.
     *
     * @param request page request
     * @return found cars
     */
    @ApiOperation("Get Cars")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request", dataType = "FilteredPageRequest", paramType = "query",
                    value = "Request contains filter string in Base64 format that represents the filter and" +
                            ""),
    })
    @GetMapping
    @PreAuthorize("hasPermission('ru.vtb.carrent.car.resource.CarResource.getCars')")
    Page<CarDto> getCars(String filter, Pageable pageable);

    /**
     * Updates Car.
     *
     * @param id     car id
     * @param carDto car
     * @return updated car
     */
    @ApiOperation(
            value = "Update a car with specific ID",
            notes = "Method provides validation errors")
    @PutMapping("/{id}")
    @PreAuthorize("hasPermission('ru.vtb.carrent.car.resource.CarResource.updateCar')")
    CarDto updateCar(@ApiParam(value = "Car id", required = true)
                     @PathVariable("id") Long id,
                     @ApiParam(value = "Car body", required = true)
                     @RequestBody CarDto carDto);

    /**
     * Put car in rent.
     *
     * @param id car id
     * @return updated car
     */
    @ApiOperation(
            value = "Put a car with specific ID in rent."
    )
    @PostMapping("/{id}/in-rent")
    @PreAuthorize("hasPermission('ru.vtb.carrent.car.resource.CarResource.inRentCar')")
    CarDto inRentCar(@ApiParam(value = "Car id", required = true)
                     @PathVariable("id") Long id,
                     @ApiParam(value = "End Date of rent", required = true)
                     @RequestParam("endDate")
                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  Date endDate);

    /**
     * Put car in stock.
     *
     * @param id car id
     * @return updated car
     */
    @ApiOperation(
            value = "Put a car with specific ID in stock."
    )
    @PostMapping("/{id}/in-stock")
    @PreAuthorize("hasPermission('ru.vtb.carrent.car.resource.CarResource.inStockCar')")
    CarDto inStockCar(@ApiParam(value = "Car id", required = true)
                      @PathVariable("id") Long id);

    /**
     * Put car on maintenance.
     *
     * @param id car id
     * @return updated car
     */
    @ApiOperation(
            value = "Put a car with specific ID on maintenance."
    )
    @PostMapping("/{id}/on-maintenance")
    @PreAuthorize("hasPermission('ru.vtb.carrent.car.resource.CarResource.onMaintenance')")
    CarDto onMaintenance(@ApiParam(value = "Car id", required = true)
                         @PathVariable("id") Long id);

    /**
     * Drop car.
     *
     * @param id car id
     * @return updated car
     */
    @ApiOperation(
            value = "Drop a car with specific ID."
    )
    @PostMapping("/{id}/drop-out")
    @PreAuthorize("hasPermission('ru.vtb.carrent.car.resource.CarResource.dropOut')")
    CarDto dropOut(@ApiParam(value = "Car id", required = true)
                   @PathVariable("id") Long id);

    /**
     * Deletes Car.
     *
     * @param id car id
     */
    @ApiOperation(value = "Delete a Car")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasPermission('ru.vtb.carrent.car.resource.CarResource.deleteCar')")
    void deleteCar(@PathVariable("id") Long id);
}
