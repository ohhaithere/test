/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2018 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.util;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
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
    public static Predicate getEqualCriteria(String value, Class javaType, CriteriaBuilder criteriaBuilder,
                                             Path path, boolean strict) {
        if (javaType.isAssignableFrom(boolean.class) || javaType.isAssignableFrom(Boolean.class)) {
            return criteriaBuilder.equal(path, Boolean.valueOf(value));
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
                Long first = isUndefined(list.get(0)) ? Long.MIN_VALUE : Long.valueOf(list.get(0));
                Long second = isUndefined(list.get(1)) ? Long.MAX_VALUE : Long.valueOf(list.get(1));
                return criteriaBuilder.between(path, first, second);
            } catch (NumberFormatException e) {
                // Just true by default
                return criteriaBuilder.conjunction();
            }
        }

        if (javaType.isAssignableFrom(int.class) || javaType.isAssignableFrom(Integer.class)) {
            try {
                Integer first = isUndefined(list.get(0)) ? Integer.MIN_VALUE : Integer.valueOf(list.get(0));
                Integer second = isUndefined(list.get(1)) ? Integer.MAX_VALUE : Integer.valueOf(list.get(1));
                return criteriaBuilder.between(path, first, second);
            } catch (NumberFormatException e) {
                // Just true by default
                return criteriaBuilder.conjunction();
            }
        }

        if (javaType.isAssignableFrom(double.class) || javaType.isAssignableFrom(Double.class)) {
            try {
                Double first = isUndefined(list.get(0)) ? Double.MIN_VALUE : Double.valueOf(list.get(0));
                Double second = isUndefined(list.get(1)) ? Double.MAX_VALUE : Double.valueOf(list.get(1));
                return criteriaBuilder.between(path, first, second);
            } catch (NumberFormatException e) {
                // Just true by default
                return criteriaBuilder.conjunction();
            }
        }

        if (javaType.isAssignableFrom(Date.class)) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
                Date startDate = isUndefined(list.get(0)) ? new GregorianCalendar(1900, 1, 1).getTime() : sdf.parse(list.get(0));
                Date endDate = isUndefined(list.get(1)) ? new GregorianCalendar(2200, 1, 1).getTime() : sdf.parse(list.get(1));
                return criteriaBuilder.between(path, startDate, endDate);
            } catch (ParseException e) {
                return criteriaBuilder.conjunction();
            }
        }

        return criteriaBuilder.between(criteriaBuilder.upper(path), list.get(0).toUpperCase(), list.get(1).toUpperCase());
    }

    private static boolean isUndefined(String s) {
        return s == null || s.trim().isEmpty() || "undefined".equalsIgnoreCase(s) || "null".equalsIgnoreCase(s);
    }

}
