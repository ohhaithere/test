/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.util;

import ru.vtb.carrent.car.dto.CarDto;

/**
 *
 */
public final class MockUtil {


    public static CarDto validTemplateDto() {
        CarDto carDto = new CarDto();
//        carDto.setName("name");
//        carDto.setSomeValue(123);
        return carDto;
    }
}
