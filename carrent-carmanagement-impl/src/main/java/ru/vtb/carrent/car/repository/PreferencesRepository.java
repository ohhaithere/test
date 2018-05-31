/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2018 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vtb.carrent.car.domain.entity.Preferences;

/**
 * Repository for preferences.
 *
 * @author Mikhail_ChenLenSon
 */
public interface PreferencesRepository extends JpaRepository<Preferences, Long> {

    /**
     * Get preferences object by name.
     *
     * @param name preferences' name
     * @return found preferences entity
     */
    Preferences findByName(String name);
}
