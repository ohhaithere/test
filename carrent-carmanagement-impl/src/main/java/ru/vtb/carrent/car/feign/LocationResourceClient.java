/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2018 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import ru.vtb.carrent.organization.resource.LocationResource;

/**
 * {@link FeignClient} for {@link LocationResource} from carrent organization service.
 *
 * @author Valiantsin_Charkashy
 */
@FeignClient(name = "organization", fallbackFactory = LocationResourceClientFallbackFactory.class)
public interface LocationResourceClient extends LocationResource {

}
