package com.brotherselectronics.fakers;

import java.util.Collection;

import static com.brotherselectronics.orderregistration.testsutils.GenericFactory.buildObjectOfAnyType;
import static java.util.Collections.emptyList;
import static java.util.List.of;
import static java.util.stream.IntStream.range;

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

    default <T> Collection<? extends T> getEntityCollection(int size) {
        if (Integer.signum(size) < 1) {
            throw new IllegalArgumentException("Size must not be " + size);
        }
        return (Collection<? extends T>) range(0, size)
                .mapToObj(e ->
                        buildObjectOfAnyType(this.getEntity().getClass()))
                .toList();
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