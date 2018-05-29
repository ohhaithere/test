/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.carrent.car.domain.entity.Car;
import ru.vtb.carrent.car.exception.EntityNotFoundException;
import ru.vtb.carrent.car.repository.CarRepository;
import ru.vtb.carrent.car.service.CarService;


/**
 * Car service implementation.
 *
 * @author Valiantsin_Charkashy
 */
@Service
@Transactional
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository repository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Car create(Car car) {
        return repository.save(car);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Car find(Long id) {
        Car found = repository.findOne(id);
        if (found == null) {
            throw new EntityNotFoundException(String.format("Car with id %s not found", id));
        }
        return found;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Car update(Car car) {
        find(car.getId());
        //Here could comes field copying
        return repository.save(car);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) {
        repository.delete(find(id));
    }

}