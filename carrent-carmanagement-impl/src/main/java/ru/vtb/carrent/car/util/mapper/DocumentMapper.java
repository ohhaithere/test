/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.util.mapper;

import com.google.common.reflect.TypeToken;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Base document mapper.
 *
 * @param <D> Domain class
 * @param <E> Dto class
 * @author Valiantsin_Charkashy
 */
@SuppressWarnings("unchecked")
public abstract class DocumentMapper<D, E> {

    protected final MapperFacade mapperFacade;

    @Autowired
    protected DocumentMapper(MapperFacade mapperFacade) {
        this.mapperFacade = mapperFacade;
    }

    /**
     * Maps document from Domain to DTO.
     *
     * @param domain Domain
     * @return DTO
     */
    public E toDto(D domain) {
        return mapperFacade.map(domain, getDtoClass());
    }

    /**
     * Maps document from Domain to DTO.
     *
     * @param domain Domain
     * @return DTO
     */
    public List<E> toDto(List<D> domain) {
        return mapperFacade.mapAsList(domain, getDtoClass());
    }

    /**
     * Maps Document from DTO to Domain.
     *
     * @param dto DTO
     * @return Domain
     */
    public D fromDto(E dto) {
        return mapperFacade.map(dto, getDomainClass());
    }

    /**
     * Maps Document from DTO to Domain.
     *
     * @param dto DTO
     * @return Domain
     */
    public List<D> fromDto(List<E> dto) {
        return mapperFacade.mapAsList(dto, getDomainClass());
    }

    private Class<D> getDomainClass() {
        final TypeToken<D> typeToken = new TypeToken<D>(getClass()) {
        };
        return (Class<D>) typeToken.getRawType();
    }

    private Class<E> getDtoClass() {
        final TypeToken<E> typeToken = new TypeToken<E>(getClass()) {
        };
        return (Class<E>) typeToken.getRawType();
    }
}
