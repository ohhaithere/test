/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2018 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.vtb.carrent.car.domain.entity.Car;
import ru.vtb.carrent.car.event.Event;
import ru.vtb.carrent.car.repository.CarRepository;
import ru.vtb.carrent.car.statemachine.StateMachineSupplier;
import ru.vtb.carrent.car.status.Status;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Service for triggering Car Events.
 *
 * @author Nikita_Puzankov
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CarScheduleServiceImpl {

    private final CarRepository carRepository;
    private final StateMachineSupplier stateMachineSupplier;

    /**
     * Scheduled job which would scan cars and send event for state transition.
     */
    @Scheduled(cron = "0 * * ? * *")
    public void checkAndPutOnMaintenance() {
        log.debug("checkAndPutOnMaintenance job start");
        final List<Car> carsInStock = carRepository.findByCurrentStatusIgnoreCase(Status.IN_STOCK.getDisplayName());
        log.debug("{} cars in stock found", carsInStock.size());
        Date now = new Date();
        for (Car car : carsInStock) {
            if (car.getDateOfNextCheck().before(now)) {
                log.debug("{} car is going to maintenance", car);
                stateMachineSupplier.getCarStateMachine(car).sendEvent(Event.GO_TO_SERVICE);
            }
        }
        log.debug("checkAndPutOnMaintenance job end");
    }

    /**
     * Scheduled job which would scan cars and send event for state transition.
     */
    @Scheduled(cron = "0 * * ? * *")
    public void checkAndRelease() {
        log.debug("checkAndRelease job start");
        final List<Car> carsOnMaintenance = carRepository.findByCurrentStatusInIgnoreCase(
                Arrays.asList(
                        Status.ON_MAINTENANCE.getDisplayName(),
                        Status.IN_RENT.getDisplayName()
                )
        );
        Date now = new Date();
        for (Car car : carsOnMaintenance) {
            if (car.getDateOfNextStatus().before(now)) {
                if (Status.IN_STOCK.getDisplayName().equalsIgnoreCase(car.getNextStatus())) {
                    if (Status.ON_MAINTENANCE.getDisplayName().equalsIgnoreCase(car.getCurrentStatus())) {
                        log.debug("{} car is going to release from maintenance", car);
                        stateMachineSupplier.getCarStateMachine(car).sendEvent(Event.SERVICE_DONE);
                    }
                    if (Status.IN_RENT.getDisplayName().equalsIgnoreCase(car.getCurrentStatus())) {
                        log.debug("{} car is going to release from rent", car);
                        stateMachineSupplier.getCarStateMachine(car).sendEvent(Event.RENT_DONE);
                    }
                }
            }
            log.debug("checkAndRelease job end");
        }
    }
}
