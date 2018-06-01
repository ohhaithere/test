package ru.vtb.carrent.car.kafka;

import org.mockito.Mockito;
import org.testng.annotations.Test;
import ru.vtb.carrent.car.service.impl.CarBookingServiceImpl;
import ru.vtb.carrent.preorder.dto.CarBookingRequest;

/**
 * Documentation template
 *
 * @author Tsimafei_Dynikau
 */
public class KafkaCarRequestListenerTest {

    @Test
    public void testListenBroker() {
        CarBookingServiceImpl bookingService = Mockito.mock(CarBookingServiceImpl.class);
        CarBookingRequest message = new CarBookingRequest(1L, null);

        new KafkaCarRequestListener(bookingService).listenBroker(message);

        Mockito.verify(bookingService).carBookingRequestHandler(message);
    }
}