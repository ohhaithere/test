/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vtb.carrent.car.domain.entity.Car;

import java.util.List;

/**
 * Repository for car.
 *
 * @author Valiantsin_Charkashy
 */
public interface CarRepository extends JpaRepository<Car, Long>, CarRepositoryCustom {

    List<Car> findByCurrentStatusIgnoreCase(String currentStatus);

    List<Car> findByCurrentStatusInIgnoreCase(List<String> currentStatus);

    List<Car> findByNextStatusIgnoreCase(String displayName);
}
