/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Car Entity.
 *
 * @author Valiantsin_Charkashy
 */
@Entity
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    /**
     * Идентификатор.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Марка автомобиля.
     */
    @Column(name = "model")
    private String model;

    /**
     * Государственный номер автомобиля.
     */
    @Column(name = "reg_number")
    private String regNumber;

    /**
     * Год выпуска.
     */
    @Column(name = "date_of_manufacture")
    private Date dateOfManufacture;

    /**
     * Пробег на последнюю дату в статусе в наличии.
     */
    @Column(name = "mileage")
    private int mileage;

    /**
     * Дата последнего ТО.
     */
    @Column(name = "date_of_last_check")
    private Date dateOfLastCheck;

    /**
     * Дата следуюшего ТО.
     */
    @Column(name = "date_of_next_check")
    private Date dateOfNextCheck;

    /**
     * Текуший статус.
     */
    @Column(name = "current_status")
    private String currentStatus;

    /**
     * Дата перехода в текущий статус.
     */
    @Column(name = "date_of_current_status")
    private Date dateOfCurrentStatus;

    /**
     * Следующий статус.
     */
    @Column(name = "next_status")
    private String nextStatus;

    /**
     * Плановая дата следуюшего статуса.
     */
    @Column(name = "date_of_next_status")
    private Date dateOfNextStatus;

    /**
     * ID Локации.
     */
    @Column(name = "location_id")
    private Long locationId;

    /**
     * Дата окончания аренды ТС.
     */
    @Column(name = "end_date_of_rent")
    private Date endDateOfRent;
}
