package com.brotherselectronics.orderregistration.services;

import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 *
 * @param <E> element thaat represent a DTO Request
 * @param <R> element that represent a DTO Response
 */
public interface IBaseService<E, R> {

    List<R> findAll();

    R findById(String id);

    R save(E dto);

    R update(E dto, String id);

    void delete(String id);

    default void merge(Object source, Object target) {
        BeanUtils.copyProperties(source, target, ignoreAtributes());
    }

    default String[] ignoreAtributes() {
        return new String[]{"id"};
    }
}