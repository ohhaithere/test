/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2018 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.repository.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import ru.vtb.carrent.car.domain.entity.Car;
import ru.vtb.carrent.car.domain.model.KeyValuePair;
import ru.vtb.carrent.car.repository.CarRepositoryCustom;
import ru.vtb.carrent.car.util.RepositoryHelper;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Repository to search cars by filter
 *
 * @author Valiantsin_Charkashy
 */
public class CarRepositoryImpl implements CarRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Car> findByFilter(List<KeyValuePair> filter, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Car> criteria = criteriaBuilder.createQuery(Car.class);
        Root<Car> root = criteria.from(Car.class);

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(Car.class)));

        if (pageable.getSort() != null) {
            criteria = criteria.orderBy(QueryUtils.toOrders(pageable.getSort(), root, criteriaBuilder));
        }

        List<Predicate> predicates = new LinkedList<>();
        if (CollectionUtils.isNotEmpty(filter)) {
            Path path;
            Class javaType;
            String key, value;
            for (KeyValuePair pair : filter) {
                key = StringUtils.isNotBlank(pair.getKey()) ? pair.getKey().trim() : "";
                path = root.get(key);
                if (path == null) {
                    throw new RuntimeException(String.format("Field with name '%s' not found", key));
                }
                javaType = path.getModel().getBindableJavaType();
                if (pair.getValue() instanceof String) {
                    value = (String) pair.getValue();
                    if (StringUtils.isNotBlank(value)) {
                        predicates.add(RepositoryHelper.getEqualCriteria(value.trim(), javaType, criteriaBuilder, path, false));
                    }
                } else if (pair.getValue() instanceof String[]) {
                    predicates.add(RepositoryHelper.getBetweenCriteria((String[]) pair.getValue(), javaType, criteriaBuilder, path));
                }
            }
        }

        criteria.where(predicates.toArray(new Predicate[predicates.size()]));
        countQuery.where(predicates.toArray(new Predicate[predicates.size()]));

        TypedQuery<Car> query = em.createQuery(criteria);
        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        Long total = em.createQuery(countQuery).getSingleResult();
        List<Car> content = total > pageable.getOffset() ? query.getResultList() : Collections.emptyList();
        return new PageImpl<>(content, pageable, total);
    }
}
