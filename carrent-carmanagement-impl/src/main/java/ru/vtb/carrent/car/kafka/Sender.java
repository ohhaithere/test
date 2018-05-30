/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2018 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.vtb.carrent.preorder.dto.MessageContainer;

/**
 * API to send messages into Kafka.
 *
 * @author Nikita_Puzankov
 */
@Service
@Slf4j
public class Sender {

    private final KafkaTemplate<String, MessageContainer> kafkaTemplate;

    public Sender(KafkaTemplate<String, MessageContainer> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String topic, MessageContainer payload) {
        log.debug("sending payload='{}' to topic='{}'", payload, topic);
        kafkaTemplate.send(topic, payload);
    }
}