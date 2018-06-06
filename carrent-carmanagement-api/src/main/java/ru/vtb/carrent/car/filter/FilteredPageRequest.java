/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2018 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

/**
 * Wrapper for filter.
 *
 * @author Valiantsin_Charkashy
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilteredPageRequest {
    private String filter;
    private Pageable pageable;
}
