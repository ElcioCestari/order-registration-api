package com.brotherselectronics.orderregistration.domains.mappers;

import java.util.List;

/**
 *
 * @param <E> - Entity
 * @param <T> - DTO Request
 * @param <R>- DTO Response
 */
public interface IBaseMapper<E, T, R> {

    R toDtoResponse(E entity);

    T toDtoRequest(E entity);

    E toEntity(T dtoRequest);

    List<R> toDtoResponseList(List<T> entityList);
}