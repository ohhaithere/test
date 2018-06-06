/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.carrent.car.domain.entity.Car;
import ru.vtb.carrent.car.domain.model.KeyValuePair;
import ru.vtb.carrent.car.event.Event;
import ru.vtb.carrent.car.event.HistoryEvent;
import ru.vtb.carrent.car.exception.EntityNotFoundException;
import ru.vtb.carrent.car.repository.CarRepository;
import ru.vtb.carrent.car.service.CarService;
import ru.vtb.carrent.car.statemachine.StateMachineSupplier;
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
@Slf4j
public class CarServiceImpl implements CarService {

    private final CarRepository repository;
    private final CarHistoryServiceImpl historyService;
    private final StateMachineSupplier stateMachineSupplier;

    @Autowired
    public CarServiceImpl(CarRepository repository,
                          CarHistoryServiceImpl historyService,
                          @Lazy StateMachineSupplier stateMachineSupplier) {
        this.repository = repository;
        this.historyService = historyService;
        this.stateMachineSupplier = stateMachineSupplier;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Car create(Car car) {
        car.setId(null);
        Car carSaved = repository.save(car);
        historyService.notify(carSaved, HistoryEvent.CREATE);
        return carSaved;
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
        return update(car, HistoryEvent.EDIT);
    }

    @Override
    public Car update(Car car, HistoryEvent event) {
        Car savedCar = find(car.getId());

        updateNotChangeableFields(car, savedCar);

        Car updatedCar = repository.save(car);
        historyService.notify(car, event);
        return updatedCar;
    }

    private void updateNotChangeableFields(Car incomingCar, Car savedCar) {
        incomingCar.setCurrentStatus(savedCar.getCurrentStatus());
        incomingCar.setDateOfCurrentStatus(savedCar.getDateOfCurrentStatus());
        incomingCar.setNextStatus(savedCar.getNextStatus());
        incomingCar.setDateOfNextCheck(savedCar.getDateOfNextCheck());
        incomingCar.setEndDateOfRent(savedCar.getEndDateOfRent());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Car inRent(Long id, Date endDate) {
        final Car car = find(id);
        car.setNextStatus(Status.IN_RENT.name());
        car.setDateOfNextStatus(new Date());
        car.setEndDateOfRent(endDate);
        Car updatedCar = update(car, HistoryEvent.STATUS_CHANGED);
        log.debug("{} car manual going to rent", car);
        stateMachineSupplier.getCarStateMachine(updatedCar).sendEvent(Event.PREORDER_BOOKING);
        return updatedCar;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Car inStock(Long id) {
        final Car car = find(id);
        car.setNextStatus(Status.IN_STOCK.name());
        car.setDateOfNextStatus(new Date());
        Car updatedCar = update(car, HistoryEvent.STATUS_CHANGED);
        if (Status.ON_MAINTENANCE.name().equalsIgnoreCase(updatedCar.getCurrentStatus())) {
            log.debug("{} car manual release from maintenance", car);
            stateMachineSupplier.getCarStateMachine(updatedCar).sendEvent(Event.SERVICE_DONE);
        }
        if (Status.IN_RENT.name().equalsIgnoreCase(updatedCar.getCurrentStatus())) {
            log.debug("{} car manual release from rent", car);
            stateMachineSupplier.getCarStateMachine(updatedCar).sendEvent(Event.RENT_DONE);
        }
        return updatedCar;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Car onMaintenance(Long id) {
        final Car car = find(id);
        car.setNextStatus(Status.ON_MAINTENANCE.name());
        car.setDateOfNextStatus(new Date());
        Car updateCar = update(car, HistoryEvent.STATUS_CHANGED);
        log.debug("{} car manual going to maintenance", car);
        stateMachineSupplier.getCarStateMachine(updateCar).sendEvent(Event.GO_TO_SERVICE);
        return updateCar;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Car dropOut(Long id) {
        final Car car = find(id);
        car.setNextStatus(Status.DROP_OUT.name());
        car.setDateOfNextStatus(new Date());
        Car updatedCar = update(car, HistoryEvent.STATUS_CHANGED);
        log.debug("{} car manual going to be dropped", updatedCar);
        stateMachineSupplier.getCarStateMachine(updatedCar).sendEvent(Event.DROP_CAR);
        return updatedCar;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) {
        historyService.notify(find(id), HistoryEvent.DELETE);
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