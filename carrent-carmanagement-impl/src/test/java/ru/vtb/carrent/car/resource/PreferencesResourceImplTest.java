/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2018 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.resource;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.vtb.carrent.car.service.PreferencesService;

/**
 * Unit test for {@link PreferencesResourceImpl}.
 *
 * @author Mikhail_ChenLenSon
 */
public class PreferencesResourceImplTest {

    private static final String PROPERTY = "property";
    private static final String VALUE = "value";

    @InjectMocks
    private PreferencesResourceImpl resource;

    @Mock
    private PreferencesService service;

    @BeforeTest
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void reset() {
        Mockito.reset(service);
    }

    @Test
    public void testGetValue() {
        when(service.find(PROPERTY)).thenReturn(VALUE);

        String value = resource.getValue(PROPERTY);

        assertEquals(value, VALUE);
    }

    @Test
    public void testUpdate() {
        resource.updatePreferences(PROPERTY, VALUE);

        verify(service).update(PROPERTY, VALUE);
    }
}
