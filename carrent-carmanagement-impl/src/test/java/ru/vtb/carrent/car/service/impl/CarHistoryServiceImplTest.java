package ru.vtb.carrent.car.service.impl;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.Test;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Documentation template
 *
 * @author Tsimafei_Dynikau
 */
public class CarHistoryServiceImplTest {
    @Mock
    private Sender sender;
    @Mock
    private LocationResourceClient locationResourceClient;
    @Mock
    private CarMapper mapper;

    private CarHistoryServiceImpl carHistoryService;

    public CarHistoryServiceImplTest() {
        MockitoAnnotations.initMocks(this);
        carHistoryService = new CarHistoryServiceImpl(sender, mapper, locationResourceClient);
    }

    @Test
    public void testNotify() {
        Car testCar = new Car().setId(1L).setLocationId(2L);
        LocationDto locationDto = new LocationDto().setId(testCar.getLocationId());
        CarDto carDto = new CarDto().setId(testCar.getId()).setLocationId(testCar.getLocationId());
        when(locationResourceClient.getLocation(testCar.getLocationId())).thenReturn(locationDto);
        when(mapper.toDto(testCar)).thenReturn(carDto);

        carHistoryService.notify(testCar, HistoryEvent.EDIT);

        MessageContainer<CarEventDTO> messageContainer = new MessageContainer<>(
                new CarEventDTO(carDto, locationDto, HistoryEvent.EDIT)
        );
        verify(sender).send(KafkaConfig.NOTIFICATION_TOPIC, messageContainer);
    }
}