/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2018 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;
import ru.vtb.carrent.preorder.dto.CarBookingRequest;
import ru.vtb.carrent.preorder.dto.MessageContainer;

import java.util.Map;

/**
 * Deserializer for CarBookingRequest from MessageContainer.
 *
 * @author Nikita_Puzankov
 */
@Slf4j
public class CarBookingRequestDeserializer implements Deserializer<CarBookingRequest> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public CarBookingRequest deserialize(String data, byte[] bytes) {
        try {
            final MessageContainer<CarBookingRequest> container = mapper.readValue(data, new TypeReference<MessageContainer<CarBookingRequest>>() {
            });
            return container.getMessage();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void close() {

    }
}
