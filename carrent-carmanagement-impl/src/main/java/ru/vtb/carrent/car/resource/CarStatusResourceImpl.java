/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vtb.carrent.car.status.Status;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Car status resource REST Controller.
 *
 * @author Nikita_Puzankov
 */
@Slf4j
@RestController
@RequestMapping("/ui/car-statuses")
public class CarStatusResourceImpl {

    /**
     * Return all possible car statuses.
     */
    @GetMapping
    public List<String> getStatuses() {
        return Arrays.stream(Status.values()).map(Status::getDisplayName).collect(Collectors.toList());
    }

}
