/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2018 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.resource.impl;

import org.springframework.stereotype.Component;
import ru.vtb.carrent.car.resource.SecurityContextHelper;
import ru.vtb.carrent.jwt.util.JwtUtils;

import java.util.List;

/**
 * Security context helper.
 *
 * @author Tsimafei_Dynikau
 */
@Component
public class SecurityContextHelperImpl implements SecurityContextHelper {

    @Override
    public List<String> getRoles() {
        return JwtUtils.getUserInfo().getRoles();
    }

    @Override
    public Long getLocationId() {
        return JwtUtils.getUserInfo().getLocationId();
    }
}
