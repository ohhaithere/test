/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.vtb.carrent.car.domain.entity.Car;
import ru.vtb.carrent.car.domain.model.KeyValuePair;
import ru.vtb.carrent.car.domain.model.SortingInfo;
import ru.vtb.carrent.car.exception.EntityNotFoundException;

import java.util.List;

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
     * Finds Cars by filter.
     *
     * @param filter filter
     * @return found cars
     *
     *  @throws EntityNotFoundException when car is not found
     */
//    List<Car> getByFilter(List<KeyValuePair> filter);

    /**
     * Finds Cars by filter
     *
     * @param filter filter
     * @param sortingInfo sortingInfo
     * @param page number of page
     * @param size page size
     * @return found car
     *
     *  @throws EntityNotFoundException when car is not found
     */
//    List<Car> getByFilter(List<KeyValuePair> filter, SortingInfo sortingInfo, Integer page, Integer size);

    /**
     * Find paginated cars
     * @param pageRequest page request
     */
    Page<Car> findPaginated(Pageable pageRequest);

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
