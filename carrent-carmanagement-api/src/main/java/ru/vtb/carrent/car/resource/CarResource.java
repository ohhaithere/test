/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.resource;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.vtb.carrent.car.dto.CarDto;

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
    @GetMapping(value = "/list")
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
     * Deletes Car.
     *
     * @param id car id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCar(@PathVariable("id") Long id);
}
