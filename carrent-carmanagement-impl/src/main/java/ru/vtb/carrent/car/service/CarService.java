/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.service;

import ru.vtb.carrent.car.domain.entity.Car;
import ru.vtb.carrent.car.exception.EntityNotFoundException;

/**
 * Car service.
 *
 * @author Valiantsin_Charkashy
 */
public interface CarService {

    /**
     * Creates Car.
     *
     * @param car car
     * @return created car
     */
    Car create(Car car);

    /**
     * Finds Car by id.
     *
     * @param id car id
     * @return found car
     *
     *  @throws EntityNotFoundException when car is not found
     */
    Car find(Long id);

    /**
     * Updates Car.
     *
     * @param car Car
     * @return updated car
     */
    Car update(Car car);

    /**
     * Deletes Car.
     *
     * @param id car id
     */
    void delete(Long id);
}
