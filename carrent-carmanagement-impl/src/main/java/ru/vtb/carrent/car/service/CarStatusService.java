/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.service;

import ru.vtb.carrent.car.domain.entity.Car;
import ru.vtb.carrent.preorder.dto.PreorderDto;

/**
 * Documentation template
 *
 * @author Tsimafei_Dynikau
 */
public interface CarStatusService {
    void putOnMaintenance(Car car);

    void release(Car car);

    void drop(Car car);

    void rent(Car car, PreorderDto preorder);
}
