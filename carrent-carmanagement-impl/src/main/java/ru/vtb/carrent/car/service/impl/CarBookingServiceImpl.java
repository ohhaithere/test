/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.service.impl;

import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;
import ru.vtb.carrent.car.domain.entity.Car;
import ru.vtb.carrent.car.event.Event;
import ru.vtb.carrent.car.service.CarService;
import ru.vtb.carrent.car.statemachine.ContextVars;
import ru.vtb.carrent.car.statemachine.StateMachineSupplier;
import ru.vtb.carrent.car.status.Status;
import ru.vtb.carrent.preorder.dto.CarBookingRequest;
import ru.vtb.carrent.preorder.dto.PreorderDto;

/**
 * Booking service.
 *
 * @author Tsimafei_Dynikau
 */
@Component
public class CarBookingServiceImpl {

    private final CarService carService;
    private final StateMachineSupplier stateMachineSupplier;

    public CarBookingServiceImpl(CarService carService, StateMachineSupplier stateMachineSupplier) {
        this.carService = carService;
        this.stateMachineSupplier = stateMachineSupplier;
    }

    public void carBookingRequestHandler(CarBookingRequest bookingRequest) {
        Car car = carService.find(bookingRequest.getCarId());
        PreorderDto preorderDto = bookingRequest.getPreorderDto();
        StateMachine<Status, Event> stateMachine = stateMachineSupplier.getCarStateMachine(car);
        stateMachine.getExtendedState().getVariables().put(ContextVars.PREORDER, preorderDto);
        stateMachine.sendEvent(Event.PREORDER_BOOKING);
    }
}
