/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.carrent.car.domain.entity.Car;
import ru.vtb.carrent.car.domain.model.KeyValuePair;
import ru.vtb.carrent.car.exception.EntityNotFoundException;
import ru.vtb.carrent.car.repository.CarRepository;
import ru.vtb.carrent.car.service.CarService;
import ru.vtb.carrent.car.status.Status;

import java.util.Date;
import java.util.List;


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

    @Override
    public Page<Car> findPaginated(Pageable pageRequest) {
        return repository.findAll(pageRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Car update(Car car) {
        Long carId = car.getId();
        if (!repository.exists(carId)) {
            throw new EntityNotFoundException(String.format("Car with id %s not found", carId));
        }
        return repository.save(car);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Car inRent(Long id, Date endDate) {
        if (!repository.exists(id)) {
            throw new EntityNotFoundException(String.format("Car with id %s not found", id));
        }
        final Car car = repository.findOne(id);
        car.setCurrentStatus(Status.IN_RENT.getDisplayName());
        car.setDateOfCurrentStatus(new Date());
        car.setNextStatus(Status.IN_STOCK.getDisplayName());
        car.setDateOfNextStatus(endDate);
        return repository.save(car);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Car inStock(Long id) {
        if (!repository.exists(id)) {
            throw new EntityNotFoundException(String.format("Car with id %s not found", id));
        }
        final Car car = repository.findOne(id);
        car.setNextStatus(Status.IN_STOCK.getDisplayName());
        car.setDateOfNextStatus(new Date());
        return repository.save(car);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Car onMaintenance(Long id) {
        if (!repository.exists(id)) {
            throw new EntityNotFoundException(String.format("Car with id %s not found", id));
        }
        final Car car = repository.findOne(id);
        car.setNextStatus(Status.ON_MAINTENANCE.getDisplayName());
        car.setDateOfNextStatus(new Date());
        return repository.save(car);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Car dropOut(Long id) {
        if (!repository.exists(id)) {
            throw new EntityNotFoundException(String.format("Car with id %s not found", id));
        }
        final Car car = repository.findOne(id);
        car.setNextStatus(Status.DROP_OUT.getDisplayName());
        car.setDateOfNextStatus(new Date());
        return repository.save(car);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) {
        repository.delete(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Car> getByFilter(List<KeyValuePair> filter, Pageable pageable) {
        return repository.findByFilter(filter, pageable);
    }

}