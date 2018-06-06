/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * Car dto.
 *
 * @author Valiantsin_Charkashy
 */
@Data
@ToString
@Accessors(chain = true)
public class CarDto {

    @ApiModelProperty(value = "ID", example = "1")
    private Long id;

    @ApiModelProperty(value = "Car model", example = "Lada Vesta")
    private String model;

    @ApiModelProperty(value = "Car registration number", example = "А777АА777")
    private String regNumber;

    @ApiModelProperty(value = "Car year", example = "2017")
    private Date dateOfManufacture;

    @ApiModelProperty(value = "Mileage (at the last time car was in stock)", example = "10123")
    private int mileage;

    @ApiModelProperty(value = "Last maintenance date", example = "2018-05-29T00:00:00.000+0000")
    private Date dateOfLastCheck;

    @ApiModelProperty(value = "Next maintenance date", example = "2018-05-29T00:00:00.000+0000")
    private Date dateOfNextCheck;

    @ApiModelProperty(value = "Current status", example = "В Наличии")
    private String currentStatus;

    @ApiModelProperty(value = "Current status last change date", example = "2018-05-29T00:00:00.000+0000")
    private Date dateOfCurrentStatus;

    @ApiModelProperty(value = "Next status", example = "В Прокате")
    private String nextStatus;

    @ApiModelProperty(value = "Planned next status change date", example = "2018-05-29T00:00:00.000+0000")
    private Date dateOfNextStatus;

    @ApiModelProperty(value = "Car location ID", example = "123")
    private Long locationId;
}
