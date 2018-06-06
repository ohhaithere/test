/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.resource;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vtb.carrent.car.domain.model.KeyValuePair;
import ru.vtb.carrent.car.dto.CarDto;
import ru.vtb.carrent.car.service.CarService;
import ru.vtb.carrent.car.status.Status;
import ru.vtb.carrent.car.util.FilterUtils;
import ru.vtb.carrent.car.util.mapper.CarMapper;
import ru.vtb.carrent.jwt.annotation.EnableJwt;
import ru.vtb.carrent.jwt.model.UserInfo;
import ru.vtb.carrent.jwt.util.JwtUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Car resource implementation.
 *
 * @author Valiantsin_Charkashy
 */
@Slf4j
@RestController
@EnableJwt
public class CarResourceImpl implements CarResource {

    private final CarService service;
    private final CarMapper mapper;
    private String admin = "Администратор";
    private String managerPo = "Менеджер по обслуживанию";
    private String managerPp = "Менеджер по прокату";
    private String ceo = "Руководство";

    /**
     * Car controller constructor.
     *
     * @param service   service
     * @param carMapper mapper
     */
    @Autowired
    public CarResourceImpl(CarService service, CarMapper carMapper) {
        this.service = service;
        this.mapper = carMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PreAuthorize("hasPermission('ru_vtb_carrent_car_resource_CarResource_createCar')")
    public CarDto createCar(@RequestBody CarDto carDto) {
        return mapper.toDto(service.create(mapper.fromDto(carDto)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PreAuthorize("hasPermission('ru_vtb_carrent_car_resource_CarResource_getCar')")
    public CarDto getCar(@PathVariable("id") Long id) {
        return mapper.toDto(service.find(id));
    }


    @PreAuthorize("hasPermission('ru_vtb_carrent_car_resource_CarResource_getCars')")
    public Page<CarDto> getCarsOld(String filter, Pageable pageable) {
        List<KeyValuePair> filterList = null;
        if (!StringUtils.isEmpty(filter)) {
            try {
                filterList = FilterUtils.getFilterList(filter);
                log.debug("Filter List: {}", filterList);
            } catch (IOException e) {
                log.error("filter ({}) could be parsed.", filter, e);
            }
        }

        return filterList == null || filterList.isEmpty()
                ? service.findPaginated(pageable).map(mapper::toDto)
                : service.getByFilter(filterList, pageable).map(mapper::toDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PreAuthorize("hasPermission('ru_vtb_carrent_car_resource_CarResource_getCars')")
    public Page<CarDto> getCars(String filter, Pageable pageable) {
        final Map<String, List<Status>> availableStatusesForRoles = new HashMap<>();
        availableStatusesForRoles.put(
                admin,
                Arrays.asList(Status.values())
        );
        availableStatusesForRoles.put(
                managerPo,
                Arrays.asList(Status.ON_MAINTENANCE)
        );
        availableStatusesForRoles.put(
                managerPp,
                Arrays.asList(Status.IN_STOCK, Status.IN_RENT)
        );
        availableStatusesForRoles.put(
                ceo,
                Arrays.asList(Status.values())
        );
        List<String> rolesWithLocationConstraint = Collections.singletonList(managerPp);
        UserInfo userInfo;
        List<String> currentUserRoles = Collections.singletonList(admin);
        Long userLocationId = 0L;
        try {
            userInfo = JwtUtils.getUserInfo();
            currentUserRoles = userInfo.getRoles();
            userLocationId = userInfo.getLocationId();
        } catch (Exception e) {
            log.error("Problems with jwt info taking", e);
        }
        log.debug("{}", currentUserRoles);
        List<KeyValuePair> filterList = new LinkedList<>();
        if (!StringUtils.isEmpty(filter)) {
            try {
                filterList = FilterUtils.getFilterList(filter);
                log.debug("Filter List: {}", filterList);
            } catch (IOException e) {
                log.error("filter ({}) could be parsed.", filter, e);
            }
        }
        filterList.add(
                new KeyValuePair(
                        "currentStatus",
                        String.join(
                                ",",
                                currentUserRoles.stream().map(
                                        role -> String.join(
                                                ",",
                                                availableStatusesForRoles.get(role)
                                                        .stream()
                                                        .map(Status::name)
                                                        .collect(
                                                                Collectors.toList()
                                                        )
                                        )
                                ).collect(
                                        Collectors.toList()
                                )
                        )
                )
        );
        Boolean locationConstraintEnabled = Boolean.FALSE;
        for (String role : rolesWithLocationConstraint) {
            locationConstraintEnabled = locationConstraintEnabled || currentUserRoles.contains(role);
        }
        log.debug("Location constraint enabled {}", locationConstraintEnabled);
        if (locationConstraintEnabled) {
            filterList.add(
                    new KeyValuePair(
                            "locationId",
                            userLocationId.toString()
                    )
            );
        }
        //МО должен видеть машины "Скоро в обслуживание" - по требованию это те автомобили
        //которые имеют nextStatus == ON_MAINTENANCE
        if (currentUserRoles.contains(managerPo)) {
            filterList.add(
                    new KeyValuePair(
                            "nextStatus",
                            Status.ON_MAINTENANCE.name()
                    )
            );
        }
        return service.getByFilter(filterList, pageable).map(mapper::toDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PreAuthorize("hasPermission('ru_vtb_carrent_car_resource_CarResource_updateCar')")
    public CarDto updateCar(@PathVariable("id") Long id,
                            @RequestBody CarDto carDto) {
        return mapper.toDto(service.update(mapper.fromDto(carDto.setId(id))));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PreAuthorize("hasPermission('ru_vtb_carrent_car_resource_CarResource_inRentCar')")
    public CarDto inRentCar(@PathVariable("id") Long id,
                            @RequestParam("endDate")
                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date endDate) {
        return mapper.toDto(service.inRent(id, endDate));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PreAuthorize("hasPermission('ru_vtb_carrent_car_resource_CarResource_inStockCar')")
    public CarDto inStockCar(@PathVariable("id") Long id) {
        return mapper.toDto(service.inStock(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PreAuthorize("hasPermission('ru_vtb_carrent_car_resource_CarResource_onMaintenance')")
    public CarDto onMaintenance(@PathVariable("id") Long id) {
        return mapper.toDto(service.onMaintenance(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PreAuthorize("hasPermission('ru_vtb_carrent_car_resource_CarResource_dropOut')")
    public CarDto dropOut(@PathVariable("id") Long id) {
        return mapper.toDto(service.dropOut(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PreAuthorize("hasPermission('ru_vtb_carrent_car_resource_CarResource_deleteCar')")
    public void deleteCar(@PathVariable("id") Long id) {
        service.delete(id);
    }

}
