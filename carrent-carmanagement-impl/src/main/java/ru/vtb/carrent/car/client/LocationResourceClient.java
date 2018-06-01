/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import ru.vtb.carrent.organization.dto.LocationDto;
import ru.vtb.carrent.organization.resource.LocationResource;

import java.util.Collections;
import java.util.List;

/**
 * LocationResourceClient for carrent-organization integration.
 *
 * @author Tsimafei_Dynikau
 */
@FeignClient(name = "carrent-organization", fallback = LocationResourceClient.LocationResourceClientFallback.class)
public interface LocationResourceClient extends LocationResource {


    /**
     * Fallback.
     *
     * @author Tsimafei_Dynikau
     */
    class LocationResourceClientFallback implements LocationResourceClient {
        @Override
        public List<LocationDto> getLocations() {
            return Collections.emptyList();
        }

        @Override
        public LocationDto getLocation(@PathVariable Long id) {
            return null;
        }
    }
}