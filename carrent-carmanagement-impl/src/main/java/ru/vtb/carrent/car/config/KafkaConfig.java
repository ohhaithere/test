package ru.vtb.carrent.car.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * Kafka configuration.
 *
 * @author Roman_Meerson
 */
@Configuration
@EnableKafka
public class KafkaConfig {
    public static final String TOPIC = "Carrent.Car.Release.Request";
    public static final String NOTIFICATION_TOPIC = "Carrent.Car.Event"; //for history
    public static final String BOOKING_REQUESTS_TOPIC = "Carrent.Car.Book.Request"; //input topic for book requests
    public static final String MGMNT_CAR_STATUSES = "Carrent.Car.Statuses";

}
