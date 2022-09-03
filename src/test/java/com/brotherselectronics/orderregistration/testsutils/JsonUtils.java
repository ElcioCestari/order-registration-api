package com.brotherselectronics.orderregistration.testsutils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class JsonUtils {
    private static final ObjectMapper CONVERTER = getJsonMapper();

    public static String convertObjectToString(Object obj) {
        try {
            return CONVERTER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static Object stringToObject(String string, Class<?> type) {
        try {
            return CONVERTER.readValue(string, type);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private static JsonMapper getJsonMapper() {
        return JsonMapper.builder()
                .addModule(new ParameterNamesModule())
                .addModule(new Jdk8Module())
                .addModule(new JavaTimeModule())
                .build();
    }

    public static <T> T convertJsonToObject(@NonNull final String resBody, @NonNull final Class<T> clazz) {
        try {
            return CONVERTER.readValue(resBody, clazz);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}