package com.brotherselectronics.orderregistration.testsutils;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.json.JsonMapper;

/**
 * Jackson 3 ships java.time, Optional and parameter-names support in databind, so no extra modules are needed.
 */
@Slf4j
public abstract class JsonUtils {
    private static final JsonMapper CONVERTER = JsonMapper.builder().build();

    public static String convertObjectToString(Object obj) {
        try {
            return CONVERTER.writeValueAsString(obj);
        } catch (JacksonException e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static <T> T stringToObject(String string, Class<T> type) {
        try {
            return CONVERTER.readValue(string, type);
        } catch (JacksonException e) {
            log.error(e.getMessage(), e);
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static <T> T convertJsonToObject(@NonNull final String resBody, @NonNull final Class<T> clazz) {
        try {
            return CONVERTER.readValue(resBody, clazz);
        } catch (JacksonException e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException(e);
        }
    }
}
