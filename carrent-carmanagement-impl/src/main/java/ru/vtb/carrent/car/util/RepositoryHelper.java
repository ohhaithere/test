/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2018 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.util;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import java.util.LinkedList;
import java.util.List;

/**
 * Helper to build query.
 *
 * @author Valiantsin_Charkashy
 */
public final class RepositoryHelper {

    private RepositoryHelper(){}


    /**
     * Converts values and creates a "LIKE" request.
     *
     * @param value           value
     * @param javaType        java type
     * @param criteriaBuilder criteria builder
     * @param path            path
     * @param strict          strict flag
     * @return {@link Predicate}
     */
    public static Predicate getEqualCriteria(String value, Class<String> javaType, CriteriaBuilder criteriaBuilder,
                                             Path<String> path, boolean strict) {
        if (javaType.isAssignableFrom(boolean.class) || javaType.isAssignableFrom(Boolean.class)) {
            try {
                return criteriaBuilder.equal(path, Boolean.valueOf(value));
            } catch (NumberFormatException e) {
                return criteriaBuilder.conjunction();
            }
        }

        if (javaType.isAssignableFrom(long.class) || javaType.isAssignableFrom(Long.class)) {
            try {
                return criteriaBuilder.equal(path, Long.valueOf(value));
            } catch (NumberFormatException e) {
                // Just true by default
                return criteriaBuilder.conjunction();
            }
        }

        if (javaType.isAssignableFrom(int.class) || javaType.isAssignableFrom(Integer.class)) {
            try {
                return criteriaBuilder.equal(path, Integer.valueOf(value));
            } catch (NumberFormatException e) {
                // Just true by default
                return criteriaBuilder.conjunction();
            }
        }

        if (javaType.isAssignableFrom(double.class) || javaType.isAssignableFrom(Double.class)) {
            try {
                return criteriaBuilder.equal(path, Double.valueOf(value));
            } catch (NumberFormatException e) {
                // Just true by default
                return criteriaBuilder.conjunction();
            }
        }

        return criteriaBuilder.like(criteriaBuilder.upper(path),
                strict ? value.toUpperCase() : "%" + value.toUpperCase() + "%");
    }

}
