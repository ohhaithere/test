/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2018 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.util;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Helper to build query.
 *
 * @author Valiantsin_Charkashy
 */
public final class RepositoryHelper {

    /**
     * Formatter for date
     *
     */
    private static final SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );

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
    public static Predicate getEqualCriteria(String value, Class javaType, CriteriaBuilder criteriaBuilder,
                                             Path path, boolean strict) {
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

    /**
     * Converts values and creates a "LIKE" request.
     *
     * @param list           range of objects
     * @param javaType        java type
     * @param criteriaBuilder criteria builder
     * @param path            path
     * @return {@link Predicate}
     */
    public static Predicate getBetweenCriteria(List<String> list, Class javaType, CriteriaBuilder criteriaBuilder, Path path) {

        if (javaType.isAssignableFrom(long.class) || javaType.isAssignableFrom(Long.class)) {
            try {
                return criteriaBuilder.between(path, Long.valueOf(list.get(0)), Long.valueOf(list.get(1)));
            } catch (NumberFormatException e) {
                // Just true by default
                return criteriaBuilder.conjunction();
            }
        }

        if (javaType.isAssignableFrom(int.class) || javaType.isAssignableFrom(Integer.class)) {
            try {
                return criteriaBuilder.between(path, Integer.valueOf(list.get(0)), Integer.valueOf(list.get(1)));
            } catch (NumberFormatException e) {
                // Just true by default
                return criteriaBuilder.conjunction();
            }
        }

        if (javaType.isAssignableFrom(double.class) || javaType.isAssignableFrom(Double.class)) {
            try {
                return criteriaBuilder.between(path, Double.valueOf(list.get(0)), Double.valueOf(list.get(1)));
            } catch (NumberFormatException e) {
                // Just true by default
                return criteriaBuilder.conjunction();
            }
        }

        if (javaType.isAssignableFrom(Date.class)) {
            try {
                return criteriaBuilder.between(path, sdf.parse(list.get(0)), sdf.parse(list.get(1)));
            } catch (ParseException e) {
                return criteriaBuilder.conjunction();
            }
        }

        return criteriaBuilder.between(criteriaBuilder.upper(path), list.get(0).toUpperCase(), list.get(1).toUpperCase());
    }

}
