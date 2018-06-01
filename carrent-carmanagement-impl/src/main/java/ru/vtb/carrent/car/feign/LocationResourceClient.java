/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */
package ru.vtb.carrent.car.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import ru.vtb.carrent.organization.dto.LocationDto;
import ru.vtb.carrent.organization.resource.LocationResource;

import java.util.Collections;
import java.util.List;


/**
 * Client to fetch location
 *
 * @author Valiantsin_Charkashy
 */
@FeignClient(name = "organization", fallback = LocationResourceClient.LocationResourceClientFallback.class)
public interface LocationResourceClient extends LocationResource {

    class LocationResourceClientFallback implements LocationResourceClient {
        @Override
        public List<LocationDto> getLocations() {
            return Collections.emptyList();
        }

        @Override
        public LocationDto getLocation(@PathVariable("id") Long id) {
            return null;
        }
    }
}
