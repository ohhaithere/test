package ru.vtb.carrent.car.service.impl;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.vtb.carrent.car.domain.entity.Car;
import ru.vtb.carrent.car.event.HistoryEvent;
import ru.vtb.carrent.car.service.CarService;
import ru.vtb.carrent.car.status.Status;
import ru.vtb.carrent.preorder.dto.CarBookingRequest;
import ru.vtb.carrent.preorder.dto.PreorderDto;

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
        Car testCar = new Car().setId(1L).setCurrentStatus(Status.IN_STOCK.name());
        CarService carService = Mockito.mock(CarService.class);
        when(carService.find(testCar.getId())).thenReturn(testCar);
        CarBookingRequest bookingRequest = new CarBookingRequest(testCar.getId(), new PreorderDto());

        CarBookingServiceImpl carBookingService = new CarBookingServiceImpl(carService);
        carBookingService.carBookingRequestHandler(bookingRequest);

        Assert.assertEquals(testCar.getNextStatus(), Status.IN_RENT.name());
        Assert.assertEquals(testCar.getCurrentStatus(), Status.IN_STOCK.name());
        verify(carService).update(testCar, HistoryEvent.STATUS_CHANGED,false);
    }
}