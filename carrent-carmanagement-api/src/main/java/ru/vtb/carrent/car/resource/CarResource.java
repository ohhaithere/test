/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.resource;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    CarDto createCar(@RequestBody CarDto carDto);

    /**
     * Gets Car by id.
     *
     * @param id car id
     * @return found car
     */
    @GetMapping("/{id}")
    CarDto getCar(@PathVariable("id") Long id);

    /**
     * Get cars.
     *
     * @param pageRequest page request
     * @return found cars
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page.")
    })
    @GetMapping
    Page<CarDto> getCars(Pageable pageRequest);

    /**
     * Updates Car.
     *
     * @param id     car id
     * @param carDto car
     * @return updated car
     */
    @PutMapping("/{id}")
    CarDto updateCar(@PathVariable("id") Long id, @RequestBody CarDto carDto);

    /**
     * Put car in rent.
     *
     * @param id car id
     * @return updated car
     */
    @PostMapping("/{id}/in-rent")
    CarDto inRentCar(@PathVariable("id") Long id,
                     @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate);

    /**
     * Put car in stock.
     *
     * @param id car id
     * @return updated car
     */
    @PostMapping("/{id}/in-stock")
    CarDto inStockCar(@PathVariable("id") Long id);

    /**
     * Put car on maintenance.
     *
     * @param id car id
     * @return updated car
     */
    @PostMapping("/{id}/on-maintenance")
    CarDto onMaintenance(@PathVariable("id") Long id);

    /**
     * Drop car.
     *
     * @param id car id
     * @return updated car
     */
    @PostMapping("/{id}/drop-out")
    CarDto dropOut(@PathVariable("id") Long id);

    /**
     * Deletes Car.
     *
     * @param id car id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCar(@PathVariable("id") Long id);
}
