/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2018 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.carrent.car.domain.entity.Preferences;
import ru.vtb.carrent.car.exception.EntityNotFoundException;
import ru.vtb.carrent.car.repository.PreferencesRepository;
import ru.vtb.carrent.car.service.PreferencesService;

/**
 * Preferences service implementation.
 *
 * @author Mikhail_ChenLenSon
 */
@Service
@Transactional
@RequiredArgsConstructor
public class PreferencesServiceImpl implements PreferencesService {

    private final PreferencesRepository repository;

    /**
     * {@inheritDoc}
     */
    @Override
    public String find(String name) {
        return getPreferences(name).getValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(String name, String value) {
        Preferences found = getPreferences(name);
        found.setValue(value);
        repository.save(found);
    }

    private Preferences getPreferences(String name) {
        Preferences found = repository.findByName(name);
        if (found == null) {
            throw new EntityNotFoundException(String.format("Preferences with name '%s' not found", name));
        }
        return found;
    }
}
