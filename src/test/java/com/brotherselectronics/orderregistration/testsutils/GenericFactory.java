package com.brotherselectronics.orderregistration.testsutils;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.FieldPredicates;

import java.util.Objects;
import java.util.Set;

public abstract class GenericFactory {

    private GenericFactory() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static <T> T buildObjectOfAnyType(Class<T> clazz) {
        return buildObjectOfAnyType(clazz, null, 1, 5);
    }

    public static <T> T buildObjectOfAnyType(Class<T> clazz, Set<String> toExcludedSet) {
        return buildObjectOfAnyType(clazz, toExcludedSet, 1, 5);
    }

    public static <T> T buildObjectOfAnyType(Class<T> clazz,
                                             Set<String> toExcludedSet,
                                             int minCollectionSize,
                                             int maxCollectionSize) {
        var parameters = new EasyRandomParameters();
        parameters.collectionSizeRange(minCollectionSize, maxCollectionSize);
        if (Objects.nonNull(toExcludedSet)) {
            toExcludedSet.forEach(field -> parameters.excludeField(FieldPredicates.named(field)));
        }
        return new EasyRandom(parameters).nextObject(clazz);
    }
}
