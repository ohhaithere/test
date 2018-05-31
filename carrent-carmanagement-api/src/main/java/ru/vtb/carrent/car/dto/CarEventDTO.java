package ru.vtb.carrent.car.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ru.vtb.carrent.organization.dto.LocationDto;

import java.util.Date;

/**
 * Car event DTO object
 *
 * @author Valiantsin_Charkashy
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class CarEventDTO {

    @ApiModelProperty(value = "Идентификатор", example = "1")
    private Long id;

    @ApiModelProperty(value = "Марка автомобиля", example = "Lada Vesta")
    private String model;

    @ApiModelProperty(value = "Государственный номер", example = "с123тт-54")
    private String regNumber;

    @ApiModelProperty(value = "Год выпуска")
    private Date dateOfManufacture;

    @ApiModelProperty(value = "Пробег на последнюю дату в статусе в наличии", example = "11")
    private int mileage;

    @ApiModelProperty(value = "Дата последнего ТО")
    private Date dateOfLastCheck;

    @ApiModelProperty(value = "Дата следуюшего ТО")
    private Date dateOfNextCheck;

    @ApiModelProperty(value = "Текуший статус", example = "в наличии")
    private String currentStatus;

    @ApiModelProperty(value = "Дата перехода в текущий статус")
    private Date dateOfCurrentStatus;

    @ApiModelProperty(value = "Следующий статус", example = "в наличии")
    private String nextStatus;

    @ApiModelProperty(value = "Плановая дата следуюшего статуса")
    private Date dateOfNextStatus;

    @ApiModelProperty(value = "Локация автомобиля")
    private LocationDto location;

    @ApiModelProperty(value = "Дата события")
    private Date eventDate;

    @ApiModelProperty(value = "Номер события", example = "1")
    private long sequenceId;

    @ApiModelProperty(value = "Тип события", example = "CREATE")
    private String eventType;

    @ApiModelProperty(value = "Локализованный тип события", example = "Создание")
    private String eventTypeDisplayName;
}
