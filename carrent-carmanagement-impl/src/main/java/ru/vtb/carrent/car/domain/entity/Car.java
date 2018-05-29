/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Car Entity
 *
 * @author Valiantsin_Charkashy
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
public class Car {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Марка автомобиля
     */
    private String model;

    /**
     * Государственный номер автомобиля
     */
    private String regNumber;

    /**
     * Год выпуска
     */
    private Date dateOfManufacture;

    /**
     * Пробег на последнюю дату в статусе в наличии
     */
    private double mileage;

    /**
     * Дата последнего ТО
     */
    private Date dateOfLastCheck;

    /**
     * Дата следуюшего ТО
     */
    private Date dateOfNextCheck;

    /**
     * Текуший статус
     */
    private String currentStatus;

    /**
     * Дата перехода в текущий статус
     */
    private Date dateOfCurrentStatus;

    /**
     * Следующий статус
     */
    private String nextStatus;

    /**
     * Плановая дата следуюшего статуса
     */
    private Date dateOfNextStatus;

}
