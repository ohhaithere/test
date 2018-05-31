/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.vtb.carrent.car.config.KafkaConfig;
import ru.vtb.carrent.car.domain.entity.Car;
import ru.vtb.carrent.car.message.CarStatusChangedMessage;
import ru.vtb.carrent.car.service.CarService;
import ru.vtb.carrent.car.status.Status;
import ru.vtb.carrent.preorder.dto.MessageContainer;

import java.time.Duration;
import java.util.Date;

/**
 * Consumer which should proceed Messages about Car's status changes to {@link Status#ON_MAINTENANCE}.
 *
 * @author Nikita_Puzankov
 */
@Service
@Slf4j
public class CarStatusChangedToMaintenanceConsumer {
    private final CarService carService;
    private final Duration defaultMaintenanceDelay = Duration.ofMinutes(5);

    public CarStatusChangedToMaintenanceConsumer(CarService carService) {
        this.carService = carService;
    }

    /**
     * Process Message
     *
     * @param messageContainer - container with message about Car's status change.
     */
    @KafkaListener(topics = KafkaConfig.MGMNT_CAR_STATUSES)
    public void receive(MessageContainer<CarStatusChangedMessage> messageContainer) {
        log.info("received payload='{}'", messageContainer);
        final CarStatusChangedMessage carStatusChangedMessage = messageContainer.getMessage();
        if (!Status.ON_MAINTENANCE.equals(carStatusChangedMessage.getNextStatus())) {
            return;
        }
        final Car car = carService.find(carStatusChangedMessage.getCarId());
        car.setCurrentStatus(Status.ON_MAINTENANCE.getDisplayName());
        car.setNextStatus(Status.IN_STOCK.getDisplayName());
        car.setDateOfCurrentStatus(new Date());
        car.setDateOfLastCheck(new Date());
        car.setDateOfNextCheck(Date.from(car.getDateOfLastCheck().toInstant().plus(defaultMaintenanceDelay)));
    }
}
