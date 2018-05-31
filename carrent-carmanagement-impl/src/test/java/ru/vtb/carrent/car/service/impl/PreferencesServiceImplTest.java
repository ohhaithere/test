/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2018 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.service.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.vtb.carrent.car.domain.entity.Preferences;
import ru.vtb.carrent.car.exception.EntityNotFoundException;
import ru.vtb.carrent.car.repository.PreferencesRepository;

/**
 * Unit test for {@link PreferencesServiceImpl}.
 *
 * @author Mikhail_ChenLenSon
 */
public class PreferencesServiceImplTest {

    private static final String PROPERTY = "property";
    private static final String VALUE = "value";
    private static final String NEW_VALUE = "newValue";

    @InjectMocks
    private PreferencesServiceImpl service;

    @Mock
    private PreferencesRepository repository;

    @Captor
    private ArgumentCaptor<Preferences> captor;

    @BeforeTest
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void reset() {
        Mockito.reset(repository);
    }

    @Test
    public void testFindProperty() {
        Preferences preferences = new Preferences(null, PROPERTY, VALUE);
        when(repository.findByName(PROPERTY)).thenReturn(preferences);

        String value = service.find(PROPERTY);

        assertEquals(value, VALUE);
    }

    @Test(expectedExceptions = EntityNotFoundException.class)
    public void testNotFoundProperty() {
        when(repository.findByName(PROPERTY)).thenReturn(null);

        service.find(PROPERTY);
    }

    @Test
    public void testUpdateProperty() {
        Preferences preferences = new Preferences(null, PROPERTY, VALUE);
        when(repository.findByName(PROPERTY)).thenReturn(preferences);

        service.update(PROPERTY, NEW_VALUE);

        verify(repository).save(captor.capture());
        assertEquals(captor.getValue().getValue(), NEW_VALUE);
    }

    @Test(expectedExceptions = EntityNotFoundException.class)
    public void testNotFoundPropertyToUpdate() {
        when(repository.findByName(PROPERTY)).thenReturn(null);

        service.update(PROPERTY, NEW_VALUE);
    }
}
