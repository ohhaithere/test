/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2018 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.message;

import lombok.Value;
import ru.vtb.carrent.car.status.Status;

/**
 * Message which should be used to request change of car's status.
 *
 * @author Nikita_Puzankov
 */
@Value
public class CarStatusChangedMessage {
    private Long carId;
    private Status previousStatus;
    private Status nextStatus;
}
