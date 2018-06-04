/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.service.impl;

import org.springframework.stereotype.Component;
import ru.vtb.carrent.car.domain.entity.Car;
import ru.vtb.carrent.car.event.HistoryEvent;
import ru.vtb.carrent.car.service.CarService;
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

    public CarBookingServiceImpl(CarService carService) {
        this.carService = carService;
    }

    public void carBookingRequestHandler(CarBookingRequest bookingRequest) {
        Car car = carService.find(bookingRequest.getCarId());
        PreorderDto preorderDto = bookingRequest.getPreorderDto();
        car.setNextStatus(Status.IN_RENT.getDisplayName());
        car.setDateOfNextStatus(preorderDto.getDateFrom());
        car.setEndDateOfRent(preorderDto.getDateTo());
        carService.update(car, HistoryEvent.STATUS_CHANGED);
    }
}
