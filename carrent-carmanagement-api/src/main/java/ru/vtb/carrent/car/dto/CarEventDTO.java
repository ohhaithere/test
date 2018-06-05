package ru.vtb.carrent.car.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ru.vtb.carrent.car.event.HistoryEvent;
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
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CarEventDTO {

    private Long id;

    @ApiModelProperty(value = "Car model", example = "BMW X6")
    private String model;

    @ApiModelProperty(value = "Car registration number", example = "А777АА777")
    private String regNumber;

    @ApiModelProperty(value = "Car year", example = "2017")
    private Date dateOfManufacture;

    @ApiModelProperty(value = "Mileage", example = "10000")
    private int mileage;

    @ApiModelProperty(value = "Last maintenance date", example = "2018-01-01T00:00:00.000+0000")
    private Date dateOfLastCheck;

    @ApiModelProperty(value = "Next maintenance date", example = "2019-01-01T00:00:00.000+0000")
    private Date dateOfNextCheck;

    @ApiModelProperty(value = "Current status", example = "В Наличии")
    private String currentStatus;

    @ApiModelProperty(value = "Current status last change date", example = "2018-05-29T00:00:00.000+0000")
    private Date dateOfCurrentStatus;

    @ApiModelProperty(value = "Next status", example = "В Прокате")
    private String nextStatus;

    @ApiModelProperty(value = "Planned next status change date", example = "2018-05-29T00:00:00.000+0000")
    private Date dateOfNextStatus;

    private LocationDto location;

    @ApiModelProperty(value = "Event date", example = "2018-05-30T00:00:00.000+0000")
    private Date eventDate;

    @ApiModelProperty(value = "Event record position", example = "10")
    private long sequenceId;

    @ApiModelProperty(value = "Event type", example = "STATUS_CHANGE")
    private String eventType;

    @ApiModelProperty(value = "Event type display name", example = "Смена статуса")
    private String eventTypeDisplayName;

    public CarEventDTO(CarDto car, LocationDto location, HistoryEvent event) {
        this.id = car.getId();
        this.model = car.getModel();
        this.regNumber = car.getRegNumber();
        this.dateOfManufacture = car.getDateOfManufacture();
        this.mileage = car.getMileage();
        this.dateOfLastCheck = car.getDateOfLastCheck();
        this.dateOfNextCheck = car.getDateOfNextCheck();
        this.currentStatus = car.getCurrentStatus();
        this.dateOfCurrentStatus = car.getDateOfCurrentStatus();
        this.nextStatus = car.getNextStatus();
        this.dateOfNextStatus = car.getDateOfNextStatus();

        this.location = location;

        this.eventDate = new Date();
        this.eventType = event.toString();
        this.eventTypeDisplayName = event.getDisplayName();
    }
}
