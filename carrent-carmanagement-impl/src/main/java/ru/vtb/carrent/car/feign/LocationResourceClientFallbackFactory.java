/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2018 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.feign;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * LocationResourceClientFallbackFactory.
 *
 * @author Roman_Meerson
 */
@Component
public class LocationResourceClientFallbackFactory implements FallbackFactory<LocationResourceClient> {
    @Override
    public LocationResourceClient create(Throwable throwable) {
        return new LocationResourceClientFallback();
    }
}
