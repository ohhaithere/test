package ru.vtb.carrent.car.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.vtb.carrent.car.config.KafkaConfig;
import ru.vtb.carrent.car.domain.entity.Car;
import ru.vtb.carrent.car.kafka.Sender;
import ru.vtb.carrent.car.message.CarStatusChangedMessage;
import ru.vtb.carrent.car.resource.LocationResourceClient;
import ru.vtb.carrent.car.status.Status;
import ru.vtb.carrent.preorder.dto.MessageContainer;

/**
 * Documentation template
 *
 * @author Tsimafei_Dynikau
 */
@Slf4j
@Component
public class CarStatusServiveImpl {

    private final LocationResourceClient locationResourceClient;
    private final Sender sender;

    public CarStatusServiveImpl(LocationResourceClient locationResourceClient, Sender sender) {
        this.locationResourceClient = locationResourceClient;
        this.sender = sender;
    }

    public void putOnMaintenance(Car car) {
        log.debug("Car would be put on maintenance");
        MessageContainer<CarStatusChangedMessage> messageContainer = new MessageContainer<>(
                new CarStatusChangedMessage(
                        car.getId(),
                        Status.get(car.getCurrentStatus()),
                        Status.ON_MAINTENANCE
                )
        );
        sender.send(KafkaConfig.MGMNT_CAR_STATUSES, messageContainer);
    }
}
