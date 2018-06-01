/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2018 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.vtb.carrent.car.config.KafkaConfig;
import ru.vtb.carrent.car.domain.entity.Car;
import ru.vtb.carrent.car.kafka.Sender;
import ru.vtb.carrent.car.message.CarStatusChangedMessage;
import ru.vtb.carrent.car.repository.CarRepository;
import ru.vtb.carrent.car.service.PreferencesService;
import ru.vtb.carrent.car.status.Status;
import ru.vtb.carrent.preorder.dto.MessageContainer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Service for managing cars' maintenance.
 *
 * @author Nikita_Puzankov
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CarMaintenanceServiceImpl {

    private static final String SERVICE_INTERVAL_PROPERTY = "service-interval";
    private static final int SERVICE_INTERVAL_DEFAULT = 30;

    private final CarRepository carRepository;
    private final Sender sender;
    private final PreferencesService preferencesService;

    /**
     * Scheduled job which would scan cars and put them on maintenance if needed.
     */
    @Scheduled(cron = "0 * * ? * *")
    public void checkAndPutOnMaintenance() {
        log.debug("checkAndPutOnMaintenance job start");
        final List<Car> carsInStock = carRepository.findByCurrentStatusIgnoreCase(Status.IN_STOCK.getDisplayName());
        log.debug("{} cars in stock found", carsInStock.size());
        Date now = new Date();
        List<Car> readyForMaintenance = new ArrayList<>();
        for (Car car : carsInStock) {
            if (car.getDateOfNextCheck().before(now)) {
                log.debug("{} car is going to maintenance", car);
                readyForMaintenance.add(car);
            }
            now = car.getDateOfNextCheck();
        }
        this.putOnMaintenance(readyForMaintenance);
        log.debug("checkAndPutOnMaintenance job end");
    }

    private void putOnMaintenance(List<Car> cars) {
        log.debug("{} cars would be put on maintenance", cars.size());
        for (Car car : cars) {
            MessageContainer<CarStatusChangedMessage> messageContainer = new MessageContainer<>(
                    new CarStatusChangedMessage(
                            car.getId(),
                            Status.get(car.getCurrentStatus()),
                            Status.ON_MAINTENANCE
                    )
            );
            sender.send(KafkaConfig.MGMNT_CAR_STATUSES, messageContainer);
        }
    }

    private int getServiceIntervalValue() {
        String value = preferencesService.find(SERVICE_INTERVAL_PROPERTY);
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            log.error(String.format("Property '%s' has invalid value: %s", SERVICE_INTERVAL_PROPERTY, value), e);
            return SERVICE_INTERVAL_DEFAULT;
        }
    }
}
