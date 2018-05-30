package ru.vtb.carrent.car.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Object contains particular info about sorting
 *
 * @author Valiantsin_Charkashy
 */
@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
public class SortingInfo {

    private boolean ascending;
    private String field;

}
