package ru.vtb.carrent.car.domain.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Object to use as key value pair
 *
 * @author Valiantsin_Charkashy
 */
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class KeyValuePair {
    private String key;
    private Object value;
}
