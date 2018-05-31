/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2018 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Preferences entity.
 *
 * @author Mikhail_ChenLenSon
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Preferences {

    /**
     * Generated ID.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Property name.
     */
    @Column(name = "name")
    private String name;

    /**
     * Property value.
     */
    @Column(name = "value")
    private String value;
}
