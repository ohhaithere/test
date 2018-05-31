/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2018 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.vtb.carrent.car.config.KafkaConfig;
import ru.vtb.carrent.car.domain.entity.Car;
import ru.vtb.carrent.car.kafka.Sender;
import ru.vtb.carrent.car.repository.CarRepository;
import ru.vtb.carrent.car.status.Status;
import ru.vtb.carrent.preorder.dto.CarReleasedDto;
import ru.vtb.carrent.preorder.dto.MessageContainer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Service for managing cars' release.
 *
 * @author Nikita_Puzankov
 */
@Service
@Slf4j
public class CarReleaseServiceImpl {

    private final CarRepository carRepository;
    private final Sender sender;

    public CarReleaseServiceImpl(CarRepository carRepository, Sender sender) {
        this.carRepository = carRepository;
        this.sender = sender;
    }

    /**
     * Scheduled job which would scan cars and send notifications about their release.
     */
    @Scheduled(cron = "0 */10 * ? * *")
    public void checkAndRelease() {
        log.debug("checkAndRelease job start");
        final List<Car> carsInStock = carRepository.findByCurrentStatusIgnoreCase(Status.IN_STOCK.getDisplayName());
        log.debug("{} cars in stock found", carsInStock.size());
        Date now = new Date();
        List<Car> readyToRelease = new ArrayList<>();
        for (Car car : carsInStock) {
            if (car.getDateOfNextCheck().after(now)) {
                log.debug("{} car is going to release", car);
                readyToRelease.add(car);
            }
            now = car.getDateOfNextCheck();
        }
        this.release(readyToRelease);
        log.debug("checkAndRelease job end");
    }

    private void release(List<Car> cars) {
        log.debug("{} cars would be released", cars.size());
        for (Car car : cars) {
            MessageContainer<CarReleasedDto> messageContainer = new MessageContainer<>(
                    new CarReleasedDto(
                            car.getId(),
                            car.getLocationId()
                    )
            );
            sender.send(KafkaConfig.TOPIC, messageContainer);
        }
    }
}
