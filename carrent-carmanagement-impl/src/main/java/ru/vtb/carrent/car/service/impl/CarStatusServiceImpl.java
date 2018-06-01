/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.vtb.carrent.car.config.KafkaConfig;
import ru.vtb.carrent.car.domain.entity.Car;
import ru.vtb.carrent.car.kafka.Sender;
import ru.vtb.carrent.car.service.CarService;
import ru.vtb.carrent.car.service.PreferencesService;
import ru.vtb.carrent.car.status.Status;
import ru.vtb.carrent.preorder.dto.CarReleasedDto;
import ru.vtb.carrent.preorder.dto.MessageContainer;
import ru.vtb.carrent.preorder.dto.PreorderDto;

import java.time.Duration;
import java.util.Date;

/**
 * Status service.
 *
 * @author Tsimafei_Dynikau
 */
@Slf4j
@Component
public class CarStatusServiceImpl {

    private static final String SERVICE_INTERVAL_PROPERTY = "service-interval";
    private static final Duration SERVICE_INTERVAL_DEFAULT = Duration.ofMinutes(5);
    private final Sender sender;
    private final CarService carService;
    private final PreferencesService preferencesService;

    public CarStatusServiceImpl(Sender sender, CarService carService, PreferencesService preferencesService) {
        this.sender = sender;
        this.carService = carService;
        this.preferencesService = preferencesService;
    }

    public void putOnMaintenance(Car car) {
        log.debug("Car would be put on maintenance");
        car.setCurrentStatus(Status.ON_MAINTENANCE.getDisplayName());
        car.setDateOfCurrentStatus(new Date());
        car.setDateOfLastCheck(new Date());
        car.setDateOfNextCheck(Date.from(car.getDateOfLastCheck().toInstant().plus(getServiceIntervalValue())));
        car.setDateOfNextStatus(null);
        car.setNextStatus(null);
        carService.update(car);
    }

    public void release(Car car) {
        log.debug("Car would be released in stock.");
        car.setCurrentStatus(Status.IN_STOCK.getDisplayName());
        car.setDateOfCurrentStatus(new Date());
        car.setDateOfNextStatus(null);
        car.setNextStatus(null);
        carService.update(car);
        //SEND KAFKA MESSAGE FOR PRE ORDER SERVICE
        MessageContainer<CarReleasedDto> messageContainer = new MessageContainer<>(
                new CarReleasedDto(
                        car.getId(),
                        car.getLocationId()
                )
        );
        sender.send(KafkaConfig.TOPIC, messageContainer);
    }

    public void drop(Car car) {
        car.setCurrentStatus(Status.DROP_OUT.getDisplayName());
        car.setDateOfCurrentStatus(new Date());
        car.setNextStatus(null);
        car.setDateOfNextStatus(null);
        carService.update(car);
    }

    private Duration getServiceIntervalValue() {
        String value = preferencesService.find(SERVICE_INTERVAL_PROPERTY);
        try {
            return Duration.ofMinutes(Integer.valueOf(value));
        } catch (NumberFormatException e) {
            log.error(String.format("Property '%s' has invalid value: %s", SERVICE_INTERVAL_PROPERTY, value), e);
            return SERVICE_INTERVAL_DEFAULT;
        }
    }

    public void rent(Car car, PreorderDto preorder) {
        car.setCurrentStatus(Status.IN_RENT.getDisplayName());
        car.setDateOfCurrentStatus(new Date());
        car.setNextStatus(Status.IN_STOCK.getDisplayName());
        car.setDateOfNextStatus(preorder.getDateTo());
        car.setLocationId(preorder.getCarReturnPoint());
        carService.update(car);
    }
}
