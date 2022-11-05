package com.brotherselectronics.orderregistration.domains.mappers;

import java.util.List;

/**
 * This interface represent the base operation that will be executed by all mappers.
 *
 * @param <E> - Entity
 * @param <T> - DTO Request
 * @param <R> - DTO Response
 */
//@Component
public interface BaseMapper<E, T, R> {

    R toDtoResponse(E entity);

    T toDtoRequest(E entity);

    E toEntity(T dtoRequest);

    List<R> toDtoResponseList(List<E> entityList);
}