/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.util.mapper;

import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Component;
import ru.vtb.carrent.car.domain.entity.Car;
import ru.vtb.carrent.car.dto.CarDto;

/**
 * Car Mapper.
 *
 * @author Valiantsin_Charkashy
 */
@Component
public class CarMapper extends DocumentMapper<Car, CarDto> {

    /**
     * Create mapper.
     *
     * @param mapperFacade facade
     */
    public CarMapper(MapperFacade mapperFacade) {
        super(mapperFacade);
    }
}
