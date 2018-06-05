/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.resource;

import org.testng.annotations.Test;
import ru.vtb.carrent.car.status.Status;

import java.util.List;

import static org.testng.Assert.assertTrue;

/**
 * Test for {@link CarStatusResourceImpl}
 *
 * @author Nikita_Puzankov
 */
public class CarStatusResourceImplTest {

    @Test
    public void testGetStatuses() {
        final List<String> statuses = new CarStatusResourceImpl().getStatuses();
        for (Status status : Status.values()) {
            assertTrue(statuses.contains(status.getDisplayName()));
        }
    }
}