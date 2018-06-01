package ru.vtb.carrent.car.service.impl;

import org.mockito.Mockito;
import org.springframework.statemachine.StateMachine;
import org.testng.annotations.Test;
import ru.vtb.carrent.car.domain.entity.Car;
import ru.vtb.carrent.car.event.Event;
import ru.vtb.carrent.car.service.CarService;
import ru.vtb.carrent.car.statemachine.StateMachineSupplier;
import ru.vtb.carrent.car.status.Status;
import ru.vtb.carrent.preorder.dto.CarBookingRequest;
import ru.vtb.carrent.preorder.dto.PreorderDto;

import java.util.HashMap;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Documentation template
 *
 * @author Tsimafei_Dynikau
 */
public class CarBookingServiceImplTest {

    @Test
    public void testCarBookingRequestHandler() {
        Car testCar = new Car().setId(1L);
        CarService carService = Mockito.mock(CarService.class);
        when(carService.find(testCar.getId())).thenReturn(testCar);
        StateMachineSupplier stateMachineSupplier = Mockito.mock(StateMachineSupplier.class);
        StateMachine<Status, Event> stateMachine = Mockito.mock(StateMachine.class, RETURNS_DEEP_STUBS);
        CarBookingRequest bookingRequest = new CarBookingRequest(testCar.getId(), new PreorderDto());
        when(stateMachineSupplier.getCarStateMachine(testCar)).thenReturn(stateMachine);
        when(stateMachine.getExtendedState().getVariables()).thenReturn(new HashMap<>());

        CarBookingServiceImpl carBookingService = new CarBookingServiceImpl(carService, stateMachineSupplier);
        carBookingService.carBookingRequestHandler(bookingRequest);

        verify(stateMachine).sendEvent(Event.PREORDER_BOOKING);
    }
}