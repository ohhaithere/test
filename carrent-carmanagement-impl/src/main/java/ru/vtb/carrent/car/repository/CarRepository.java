/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vtb.carrent.car.domain.entity.Car;

/**
 * Repository for car.
 *
 * @author Valiantsin_Charkashy
 */
public interface CarRepository extends JpaRepository<Car, Long>, CarRepositoryCustom {

}
