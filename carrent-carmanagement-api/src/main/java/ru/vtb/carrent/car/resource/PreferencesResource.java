/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2018 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.resource;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Preferences resource interface.
 *
 * @author Mikhail_ChenLenSon
 */
@RequestMapping("/ui/preferences")
public interface PreferencesResource {

    /**
     * Gets preferences by name.
     *
     * @param name preferences' name
     * @return found preferences
     */
    @ApiOperation("Get preferences value by name")
    @GetMapping("/{name}")
    String getValue(@PathVariable("name") String name);

    /**
     * Updates preferences.
     *
     * @param name preferences' name
     * @param value value to store
     */
    @ApiOperation("Set preferences value by name")
    @PutMapping("/{name}")
    void updatePreferences(@PathVariable("name") String name, @RequestParam(name = "value") String value);
}
