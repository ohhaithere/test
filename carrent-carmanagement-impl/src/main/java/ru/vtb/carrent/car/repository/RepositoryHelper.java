package ru.vtb.carrent.car.repository;

import org.apache.commons.lang3.StringUtils;
import ru.vtb.carrent.car.domain.model.SortingInfo;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Helper to build query
 *
 * @author Valiantsin_Charkashy
 */
public final class RepositoryHelper {

    private RepositoryHelper(){};


    /**
     * Converts values and creates a "LIKE" request
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

    /**
     * Converts values from UI to types provided by hibernate and creates an "OR" request
     *
     * @param values          list of values
     * @param javaType        java type
     * @param criteriaBuilder criteria builder
     * @param path            path
     * @return {@link Predicate}
     */
    public static Predicate getOrCriteria(List values, Class<String> javaType, CriteriaBuilder criteriaBuilder,
                                          Path<String> path) {
        List<Predicate> predicates = new LinkedList<>();
        if (javaType.isAssignableFrom(String.class)) {
            for (Object value : values) {
                String val = (String) value;
                if (StringUtils.isNotBlank(val)) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.upper(path), "%" + val.trim().toUpperCase() + "%"));
                }
            }
        }

        return criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
    }

    /**
     * Sets sorting for a criteria
     *
     * @param sortingInfo     sorting info
     * @param criteria        criteria
     * @param root            root
     * @param criteriaBuilder criteria builder
     */
    @SuppressWarnings("unchecked")
    public static void setSortingCriteria(SortingInfo sortingInfo, CriteriaQuery criteria,
                                          Root root, CriteriaBuilder criteriaBuilder) {
        if (sortingInfo != null && StringUtils.isNotBlank(sortingInfo.getField())) {
            String sortingField = sortingInfo.getField().trim();
            Path<Object> path = root.get(sortingField);
            if (path == null) {
                throw new RuntimeException(String.format("Sorting field with name '%s' not found", sortingField));
            }

            if (!path.getJavaType().isAssignableFrom(Set.class)) {
                if (sortingInfo.isAscending()) {
                    criteria.orderBy(criteriaBuilder.asc(path));
                } else {
                    criteria.orderBy(criteriaBuilder.desc(path));
                }
            }
        }
    }
}
