/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.listener;

import org.springframework.stereotype.Component;
import ru.vtb.carrent.car.domain.entity.Car;
import ru.vtb.carrent.car.event.Event;
import ru.vtb.carrent.car.kafka.Sender;
import ru.vtb.carrent.car.resource.LocationResourceClient;
import ru.vtb.carrent.car.status.Status;

/**
 * Listener for car statuses.
 *
 * @author Tsimafei_Dynikau
 */
@Component
public class CarStateMachineListener {
    private final LocationResourceClient locationResourceClient;
    private final Sender sender;

    public CarStateMachineListener(LocationResourceClient locationResourceClient, Sender sender) {
        this.locationResourceClient = locationResourceClient;
        this.sender = sender;
    }

    public void changeCarStatus(Car car, Status status, Event event) {
        car.setCurrentStatus(status.getDisplayName());
    }
}
