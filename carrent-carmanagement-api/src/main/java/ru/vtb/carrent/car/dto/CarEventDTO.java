package ru.vtb.carrent.car.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
public class CarEventDTO {

    private Long id;

    private String model;

    private String regNumber;

    private Date dateOfManufacture;

    private int mileage;

    private Date dateOfLastCheck;

    private Date dateOfNextCheck;

    private String currentStatus;

    private Date dateOfCurrentStatus;

    private String nextStatus;

    private Date dateOfNextStatus;

    private LocationDto location;

    private Date eventDate;

    private long sequenceId;

    private String eventType;

    private String eventTypeDisplayName;
}
