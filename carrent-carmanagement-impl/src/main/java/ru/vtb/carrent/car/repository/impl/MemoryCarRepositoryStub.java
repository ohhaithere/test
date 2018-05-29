/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.repository.impl;


import org.springframework.stereotype.Service;
import ru.vtb.carrent.car.domain.entity.Car;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Stub class to store Cars in memory
 *
 * @author Valiantsin_Charkashy
 */
@Service
public class MemoryCarRepositoryStub {

    private final Map<Long, Car> cars = new ConcurrentHashMap<>();

    public Car save(Car car) {
        cars.put(car.getId(), car);
        return car;
    }

    public Car findOne(Long id) {
        return cars.get(id);
    }

    public Car update(Car car) {
        return save(car);
    }

    public void delete(Long id) {
        cars.remove(id);
    }

    public boolean exists(Long carId) {
        return cars.containsKey(carId);
    }
}
