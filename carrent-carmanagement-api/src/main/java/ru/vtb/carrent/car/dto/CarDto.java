/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * Car dto.
 *
 * @author Valiantsin_Charkashy
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class CarDto {

    @ApiModelProperty(value = "Идентификатор", example = "1")
    private Long id;

    @ApiModelProperty(value = "Марка автомобиля", example = "Lada Vesta")
    private String model;

    @ApiModelProperty(value = "Государственный номер", example = "с123тт-54")
    private String regNumber;

    @ApiModelProperty(value = "Год выпуска", example = "28/05/2018")
    private Date dateOfManufacture;

    @ApiModelProperty(value = "Пробег на последнюю дату в статусе в наличии", example = "11.1")
    private double mileage;

    @ApiModelProperty(value = "Дата последнего ТО", example = "29/05/2018")
    private Date dateOfLastCheck;

    @ApiModelProperty(value = "Дата следуюшего ТО", example = "29/05/2020")
    private Date dateOfNextCheck;

    @ApiModelProperty(value = "Текуший статус", example = "в наличии")
    private String currentStatus;

    @ApiModelProperty(value = "Дата перехода в текущий статус", example = "30/05/2018")
    private Date dateOfCurrentStatus;

    @ApiModelProperty(value = "Следующий статус", example = "в наличии")
    private String nextStatus;

    @ApiModelProperty(value = "Плановая дата следуюшего статуса", example = "31/05/2018")
    private Date dateOfNextStatus;
}
