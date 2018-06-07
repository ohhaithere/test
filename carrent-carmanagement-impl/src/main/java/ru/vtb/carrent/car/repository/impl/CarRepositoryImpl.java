/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2018 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import ru.vtb.carrent.car.domain.entity.Car;
import ru.vtb.carrent.car.domain.model.KeyValuePair;
import ru.vtb.carrent.car.domain.model.OrValue;
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
 * Repository to search cars by filter.
 *
 * @author Valiantsin_Charkashy
 */
@Slf4j
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
            String key;
            for (KeyValuePair pair : filter) {
                key = StringUtils.isNotBlank(pair.getKey()) ? pair.getKey().trim() : "";
                path = getPath(root, key);
                if (path == null) {
                    log.error(String.format("Field with name {%s} not found", key));
                    return new PageImpl<>(Collections.emptyList());
                }
                javaType = path.getModel().getBindableJavaType();
                if (pair.getValue() instanceof String) {
                    predicates.add(
                            RepositoryHelper.getEqualCriteria(
                                    ((String) pair.getValue()).trim(),
                                    javaType,
                                    criteriaBuilder,
                                    path,
                                    false
                            )
                    );
                } else if (pair.getValue() instanceof List) {
                    predicates.add(RepositoryHelper.getBetweenCriteria((List<String>) pair.getValue(), javaType, criteriaBuilder, path));
                } else if (pair.getValue() instanceof OrValue) {
                    final OrValue orValue = (OrValue) pair.getValue();
                    String anotherKey = StringUtils.isNotBlank(orValue.getAnotherKey()) ? orValue.getAnotherKey().trim()
                            : "";
                    Path anotherPath = getPath(root, anotherKey);
                    if (anotherPath == null) {
                        log.error(String.format("Field with name {%s} not found", anotherKey));
                        return new PageImpl<>(Collections.emptyList());
                    }
                    Class anotherJavaType = anotherPath.getModel().getBindableJavaType();
                    predicates.add(
                            criteriaBuilder.or(
                                    RepositoryHelper.getEqualCriteria(
                                            ((String) orValue.getOneValue()).trim(),
                                            javaType,
                                            criteriaBuilder,
                                            path,
                                            false
                                    ),
                                    RepositoryHelper.getEqualCriteria(
                                            ((String) orValue.getAnotherValue()).trim(),
                                            anotherJavaType,
                                            criteriaBuilder,
                                            anotherPath,
                                            false
                                    )
                            )
                    );
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

    private Path getPath(Root<Car> root, String key) {
        try {
            return root.get(key);
        } catch (IllegalArgumentException e) {
            log.error(String.format("Field with name {%s} not found", key), e);
        }
        return null;
    }
}
