package com.brotherselectronics.fakers;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public interface EntityFake<T, E, R> {

    T getEntity();
    E getRequestDTO();
    R getResponseDTO();

    default Collection< ? extends T> getEntityCollection() {
        return getEntity() != null ? List.of(getEntity()) : Collections.emptyList();
    }

    default Collection< ? extends E> getRequestDTOCollection() {
        return getEntity() != null ? List.of(getRequestDTO()) : Collections.emptyList();
    }

    default Collection< ? extends R> getResponseDTOCollection() {
        return getEntity() != null ? List.of(getResponseDTO()) : Collections.emptyList();
    }

    default Collection< ? extends T> getEmptyEntityCollection() {
        return Collections.emptyList();
    }

    default Collection< ? extends E> getEmptyRequestDTOCollection() {
        return Collections.emptyList();
    }

    default Collection< ? extends R> getEmptyResponseDTOCollection() {
        return Collections.emptyList();
    }
}