/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2018 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.vtb.carrent.car.service.PreferencesService;

/**
 * Preferences resource implementation.
 *
 * @author Mikhail_ChenLenSon
 */
@RestController
@RequiredArgsConstructor
public class PreferencesResourceImpl implements PreferencesResource {

    private final PreferencesService service;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue(String name) {
        return service.find(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePreferences(String name, String value) {
        service.update(name, value);
    }
}
