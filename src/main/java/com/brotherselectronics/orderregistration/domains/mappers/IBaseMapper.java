package com.brotherselectronics.orderregistration.domains.mappers;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * @param <E> - Entity
 * @param <T> - DTO Request
 * @param <R>- DTO Response
 */
//@Component
public interface IBaseMapper<E, T, R> {

    R toDtoResponse(E entity);

    T toDtoRequest(E entity);

    E toEntity(T dtoRequest);

    List<R> toDtoResponseList(List<E> entityList);
}