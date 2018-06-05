/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2018 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
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
    @PreAuthorize("hasPermission('ru.vtb.carrent.car.resource.PreferencesResource.getValue')")
    public String getValue(@PathVariable("name") String name) {
        return service.find(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PreAuthorize("hasPermission('ru.vtb.carrent.car.resource.PreferencesResource.updatePreferences')")
    public void updatePreferences(@PathVariable("name") String name, @RequestParam(name = "value") String value) {
        service.update(name, value);
    }
}
