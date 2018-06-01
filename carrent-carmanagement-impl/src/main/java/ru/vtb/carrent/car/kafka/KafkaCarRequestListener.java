/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2018 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.vtb.carrent.car.config.KafkaConfig;
import ru.vtb.carrent.car.service.impl.CarBookingServiceImpl;
import ru.vtb.carrent.preorder.dto.CarBookingRequest;

/**
 * Listen message for car booking.
 *
 * @author Tsimafei_Dynikau
 */
@Component
@ConditionalOnProperty(prefix = "spring.kafka", name = "enabled", havingValue = "true")
@Slf4j
public class KafkaCarRequestListener {
    private final CarBookingServiceImpl carBookingService;

    public KafkaCarRequestListener(CarBookingServiceImpl carBookingService) {
        this.carBookingService = carBookingService;
    }

    /**
     * Listen broker.
     *
     * @param message message from queue
     */
    @KafkaListener(topics = KafkaConfig.BOOKING_REQUESTS_TOPIC)
    public void listenBroker(CarBookingRequest message) {
        log.info("CarBookingRequest received from Kafka: {}", message);
        carBookingService.carBookingRequestHandler(message);
    }
}
