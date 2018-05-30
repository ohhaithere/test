package ru.vtb.carrent.car.repository.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.vtb.carrent.car.domain.entity.Car;
import ru.vtb.carrent.car.domain.model.KeyValuePair;
import ru.vtb.carrent.car.repository.CarRepositoryCustom;
import ru.vtb.carrent.car.repository.RepositoryHelper;
import ru.vtb.carrent.car.domain.model.SortingInfo;

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
    public List<Car> findByFilter(List<KeyValuePair> filter) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Car> criteria = criteriaBuilder.createQuery(Car.class);
        Root<Car> root = criteria.from(Car.class);

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(Car.class)));

        List<Predicate> predicates = new LinkedList<>();
        if (CollectionUtils.isNotEmpty(filter)) {
            Path<String> path;
            Class<String> javaType;
            String key, value;
            List valuesList;
            for (KeyValuePair pair : filter) {
                key = StringUtils.isNotBlank(pair.getKey()) ? pair.getKey().trim() : "";
                path = root.get(key);
                if (path == null) {
                    throw new RuntimeException(String.format("Field with name '%s' not found", key));
                }

                javaType = path.getModel().getBindableJavaType();
                value = (String) pair.getValue();
                if (StringUtils.isNotBlank(value)) {
                    predicates.add(RepositoryHelper.getEqualCriteria(value.trim(), javaType, criteriaBuilder, path, true));
                }

            }
        }

        criteria.where(predicates.toArray(new Predicate[predicates.size()]));
        countQuery.where(predicates.toArray(new Predicate[predicates.size()]));

        return em.createQuery(criteria).getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Car> findByFilter(List<KeyValuePair> filter, SortingInfo sortingInfo, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Car> criteria = criteriaBuilder.createQuery(Car.class);
        Root<Car> root = criteria.from(Car.class);

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(Car.class)));

        RepositoryHelper.setSortingCriteria(sortingInfo, criteria, root, criteriaBuilder);

        List<Predicate> predicates = new LinkedList<>();
        if (CollectionUtils.isNotEmpty(filter)) {
            Path<String> path;
            Class<String> javaType;
            String key, value;
            for (KeyValuePair pair : filter) {
                key = StringUtils.isNotBlank(pair.getKey()) ? pair.getKey().trim() : "";
                if ("site".equalsIgnoreCase(key)) {
                    key = "siteId";
                }

                path = root.get(key);
                if (path == null) {
                    throw new RuntimeException(String.format("Field with name '%s' not found", key));
                }

                javaType = path.getModel().getBindableJavaType();

                value = (String) pair.getValue();
                if (StringUtils.isNotBlank(value)) {
                    predicates.add(RepositoryHelper.getEqualCriteria(value.trim(), javaType, criteriaBuilder, path, false));
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

    /**
     * Converts values and creates a "OR" request.
     *
     * @param values          list of values
     * @param javaType        type of value
     * @param criteriaBuilder {@link CriteriaBuilder}
     * @param path            {@link Path}
     * @param strict          strict flag
     * @return {@link Predicate}
     */
    private Predicate createOrCriteria(List values, Class<String> javaType, CriteriaBuilder criteriaBuilder,
                                       Path<String> path, boolean strict) {
        List<Predicate> predicates = new LinkedList<>();
        if (javaType.isAssignableFrom(String.class)) {
            String pattern;
            for (Object value : values) {
                String val = (String) value;
                if (StringUtils.isNotBlank(val)) {
                    pattern = strict ? val.trim().toUpperCase() : "%" + val.trim().toUpperCase() + "%";
                    predicates.add(criteriaBuilder.like(criteriaBuilder.upper(path), pattern));
                }
            }
        }
        return criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
    }
}
