/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.service.impl;

import ru.vtb.carrent.car.config.KafkaConfig;
import ru.vtb.carrent.car.domain.entity.Car;
import ru.vtb.carrent.car.dto.CarDto;
import ru.vtb.carrent.car.dto.CarEventDTO;
import ru.vtb.carrent.car.event.HistoryEvent;
import ru.vtb.carrent.car.feign.LocationResourceClient;
import ru.vtb.carrent.car.kafka.Sender;
import ru.vtb.carrent.car.util.mapper.CarMapper;
import ru.vtb.carrent.organization.dto.LocationDto;
import ru.vtb.carrent.preorder.dto.MessageContainer;

/**
 * Service for car history tracking.
 *
 * @author Tsimafei_Dynikau
 */
public class CarHistoryServiceImpl {
    private final Sender sender;
    private final CarMapper mapper;
    private final LocationResourceClient locationResourceClient;

    public CarHistoryServiceImpl(Sender sender, CarMapper mapper, LocationResourceClient locationResourceClient) {
        this.sender = sender;
        this.mapper = mapper;
        this.locationResourceClient = locationResourceClient;
    }

    public void notify(Car car, HistoryEvent event) {
        CarDto carDto = mapper.toDto(car);
        LocationDto locationDto = locationResourceClient.getLocation(car.getLocationId());
        MessageContainer<CarEventDTO> message = new MessageContainer<>(
                new CarEventDTO(carDto, locationDto, event)
        );
        sender.send(KafkaConfig.NOTIFICATION_TOPIC, message);
    }
}
