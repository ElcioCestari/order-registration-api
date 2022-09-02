package com.brotherselectronics.fakers;

import java.util.Collection;

import static java.util.Collections.emptyList;
import static java.util.List.of;

/**
 * @param <T> - Entity
 * @param <E> - RequestDTO
 * @param <R> - ResponseDTO
 */
public interface EntityFake<T, E, R> {

    T getEntity();

    E getRequestDTO();

    R getResponseDTO();

    default Collection<? extends T> getEntityCollection() {
        return getEntity() != null ? of(getEntity()) : emptyList();
    }

    default Collection<? extends E> getRequestDTOCollection() {
        return getEntity() != null ? of(getRequestDTO()) : emptyList();
    }

    default Collection<? extends R> getResponseDTOCollection() {
        return getEntity() != null ? of(getResponseDTO()) : emptyList();
    }

    default Collection<? extends T> getEmptyEntityCollection() {
        return emptyList();
    }

    default Collection<? extends E> getEmptyRequestDTOCollection() {
        return emptyList();
    }

    default Collection<? extends R> getEmptyResponseDTOCollection() {
        return emptyList();
    }
}