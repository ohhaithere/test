/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.vtb.carrent.car.domain.entity.Car;
import ru.vtb.carrent.car.domain.model.KeyValuePair;
import ru.vtb.carrent.car.event.HistoryEvent;
import ru.vtb.carrent.car.exception.EntityNotFoundException;

import java.util.Date;
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
     * @param pageRequest page request
     * @return found car
     *
     *  @throws EntityNotFoundException when car is not found
     */
    Page<Car> getByFilter(List<KeyValuePair> filter, Pageable pageRequest);

    /**
     * Find paginated cars.
     *
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
     * Updates Car with custom history event.
     *
     * @param car   Car
     * @param event HistoryEvent
     * @return updated car
     */
    Car update(Car car, HistoryEvent event);

    /**
     * Put Car into rent.
     *
     * @param id      car id
     * @param endDate end date
     * @return updated car
     */
    Car inRent(Long id, Date endDate);

    /**
     * Put Car into stock.
     *
     * @param id car id
     * @return updated car
     */
    Car inStock(Long id);

    /**
     * Put Car on maintenance.
     *
     * @param id car id
     * @return updated car
     */
    Car onMaintenance(Long id);

    /**
     * Drop Car.
     *
     * @param id car id
     * @return updated car
     */
    Car dropOut(Long id);

    /**
     * Deletes Car.
     *
     * @param id car id
     */
    void delete(Long id);

}
