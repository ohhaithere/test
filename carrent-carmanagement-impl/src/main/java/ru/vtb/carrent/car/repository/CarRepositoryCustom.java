package ru.vtb.carrent.car.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.vtb.carrent.car.domain.entity.Car;
import ru.vtb.carrent.car.domain.model.KeyValuePair;
import ru.vtb.carrent.car.domain.model.SortingInfo;

import java.util.List;

/**
 * Custom repository for Car
 *
 * @author Valiantsin_Charkashy
 */
public interface CarRepositoryCustom {

    /**
     * Fetch filter of items for the specified filter.
     *
     * @param filter list of {@link KeyValuePair } objects. Represents the filter
     * @return list with server entities
     */
    List<Car> findByFilter(List<KeyValuePair> filter);

    /**
     * Fetch filter of items for the specified filter.
     *
     * @param filter   list of {@link KeyValuePair} objects. Represents the filter
     * @param pageable filter information
     * @return {@link Page}
     */
    Page<Car> findByFilter(List<KeyValuePair> filter, SortingInfo sortingInfo, Pageable pageable);
}
