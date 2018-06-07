/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2018 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Value for {@link KeyValuePair} which provides support for OR conditions.
 *
 * @author Nikita_Puzankov
 */
@Data
@AllArgsConstructor
public class OrValue {
    Object oneValue;
    String anotherKey;
    Object anotherValue;
}
