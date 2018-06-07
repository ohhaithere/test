/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2018 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.util;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Helper to build query.
 *
 * @author Valiantsin_Charkashy
 */
@Slf4j
public final class RepositoryHelper {

    private RepositoryHelper() {
    }


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
        try {
            if (isBoolean(javaType)) {
                return criteriaBuilder.equal(path, Boolean.valueOf(value));
            } else if (isLong(javaType)) {
                return criteriaBuilder.equal(path, Long.valueOf(value));
            } else if (isInteger(javaType)) {
                return criteriaBuilder.equal(path, Integer.valueOf(value));
            } else if (isDouble(javaType)) {
                return criteriaBuilder.equal(path, Double.valueOf(value));
            }
        } catch (NumberFormatException e) {
            return criteriaBuilder.conjunction();
        }

        if (value.contains(",")) {
            List<String> multipleStringValue = Arrays.asList(value.split(","));
            return path.in(multipleStringValue);
        }

        return criteriaBuilder.like(criteriaBuilder.upper(path),
                strict ? value.toUpperCase() : "%" + value.toUpperCase() + "%");
    }


    /**
     * Converts values and creates a "LIKE" request.
     *
     * @param list            range of objects
     * @param javaType        java type
     * @param criteriaBuilder criteria builder
     * @param path            path
     * @return {@link Predicate}
     */
    public static Predicate getBetweenCriteria(List<String> list, Class javaType, CriteriaBuilder criteriaBuilder, Path path) {

        try {
            if (isLong(javaType)) {
                Long first = getLong(list.get(0), Long.MIN_VALUE);
                Long second = getLong(list.get(1), Long.MAX_VALUE);
                return criteriaBuilder.between(path, first, second);
            } else if (isInteger(javaType)) {
                Integer first = getInteger(list.get(0), Integer.MIN_VALUE);
                Integer second = getInteger(list.get(1), Integer.MAX_VALUE);
                return criteriaBuilder.between(path, first, second);
            } else if (isDouble(javaType)) {
                Double first = getDouble(list.get(0), Double.MIN_VALUE);
                Double second = getDouble(list.get(1), Double.MAX_VALUE);
                return criteriaBuilder.between(path, first, second);
            } else if (javaType.isAssignableFrom(Date.class)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date startDate = isUndefined(list.get(0))
                        ? new GregorianCalendar(1900, 1, 1).getTime() : sdf.parse(list.get(0));
                Date endDate = isUndefined(list.get(1))
                        ? new GregorianCalendar(2200, 1, 1).getTime() : sdf.parse(list.get(1));
                return criteriaBuilder.between(path, startDate, endDate);
            }
        } catch (ParseException | NumberFormatException e) {
            log.warn("Incorrect criteria parsing", e);
            return criteriaBuilder.conjunction();
        }

        return criteriaBuilder.between(criteriaBuilder.upper(path),
                list.get(0).toUpperCase(), list.get(1).toUpperCase());
    }

    private static Long getLong(String item, long defaultValue) {
        return isUndefined(item) ? Long.valueOf(defaultValue) : Long.valueOf(item);
    }

    private static Integer getInteger(String item, int defaultValue) {
        return isUndefined(item) ? Integer.valueOf(defaultValue) : Integer.valueOf(item);
    }

    private static Double getDouble(String item, double defaultValue) {
        return isUndefined(item) ? Double.valueOf(defaultValue) : Double.valueOf(item);
    }

    private static boolean isUndefined(String s) {
        return s == null || s.trim().isEmpty() || "undefined".equalsIgnoreCase(s) || "null".equalsIgnoreCase(s);
    }

    private static boolean isDouble(Class javaType) {
        return javaType.isAssignableFrom(double.class) || javaType.isAssignableFrom(Double.class);
    }

    private static boolean isInteger(Class javaType) {
        return javaType.isAssignableFrom(int.class) || javaType.isAssignableFrom(Integer.class);
    }

    private static boolean isLong(Class javaType) {
        return javaType.isAssignableFrom(long.class) || javaType.isAssignableFrom(Long.class);
    }

    private static boolean isBoolean(Class javaType) {
        return javaType.isAssignableFrom(boolean.class) || javaType.isAssignableFrom(Boolean.class);
    }
}
