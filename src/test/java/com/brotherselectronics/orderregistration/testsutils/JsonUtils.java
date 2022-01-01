package com.brotherselectronics.orderregistration.testsutils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

    private static final ObjectMapper CONVERTER = new ObjectMapper();

    public static String convertObjectToString(Object obj) {
        try {
            return CONVERTER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
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

}