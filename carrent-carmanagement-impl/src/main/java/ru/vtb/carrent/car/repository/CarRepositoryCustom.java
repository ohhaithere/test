/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2018 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.vtb.carrent.car.domain.entity.Car;
import ru.vtb.carrent.car.domain.model.KeyValuePair;

import java.util.List;

/**
 * Custom repository for Car.
 *
 * @author Valiantsin_Charkashy
 */
@FunctionalInterface
public interface CarRepositoryCustom {

    /**
     * Fetch filter of items for the specified filter.
     *
     * @param filter   list of {@link KeyValuePair} objects. Represents the filter
     * @param pageable filter information
     * @return {@link Page}
     */
    Page<Car> findByFilter(List<KeyValuePair> filter, Pageable pageable);
}
