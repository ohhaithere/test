/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2018 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.resource;

import java.util.List;

/**
 * Security context helper interface.
 *
 * @author Tsimafei_Dynikau
 */
public interface SecurityContextHelper {

    List<String> getRoles();

    Long getLocationId();
}
