/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.resource;

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
     *
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
     * Updates Car.
     *
     * @param id          car id
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
