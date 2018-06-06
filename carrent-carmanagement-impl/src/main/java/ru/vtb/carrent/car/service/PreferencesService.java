/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2018 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.service;

/**
 * Preference service.
 *
 * @author Mikhail_ChenLenSon
 */
public interface PreferencesService {

    /**
     * Gets preferences value by name.
     *
     * @param name preferences' name
     * @return found value
     */
    String find(String name);

    /**
     * Updates preferences.
     *
     * @param name preferences' name
     * @param value value to store
     */
    void update(String name, String value);
}
