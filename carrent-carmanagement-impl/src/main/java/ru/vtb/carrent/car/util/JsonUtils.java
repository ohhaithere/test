/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * JSON to Bean or Maps utilities.
 *
 * @author Valiantsin_Charkashy
 */
public final class JsonUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtils.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static ObjectReader READER;
    private static ObjectWriter WRITER;

    static {
        MAPPER.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        READER = MAPPER.reader();
        WRITER = MAPPER.writer();
    }

    private JsonUtils() {
    }

    /**
     * Converts JSON string to Java object.
     *
     * @param json   - json to convert
     * @param toType - target bean type (class)
     * @return bean
     * @throws IOException problems of conversation
     */
    public static <T> T jsonToBean(String json, Class<T> toType) throws IOException {
        return READER.forType(toType).readValue(json);
    }

    /**
     * Converts JSON string to Java object.
     *
     * @param json      - json content string
     * @param reference type reference, in case if the result bean is parametrized
     * @return parametrized bean instance
     * @throws IOException problems of conversation
     */
    public static <T> T jsonToBean(String json, TypeReference<?> reference) throws IOException {
        return MAPPER.readValue(json, reference);
    }

    /**
     * Apply JSON string to Java object.
     *
     * @param json       - json to convert
     * @param toInstance - target bean instance
     * @return bean
     * @throws IOException problems of conversation
     */
    public static <T> T jsonToInstance(String json, Object toInstance) throws IOException {
        return READER.withValueToUpdate(toInstance).readValue(json);
    }

    /**
     * Converts JSON to map.
     *
     * @param json - json to convert
     * @return map
     * @throws IOException problems of conversation
     */
    public static Map<String, Object> jsonToMap(String json) throws IOException {
        return READER.forType(Map.class).readValue(json);
    }

    /**
     * Serializes bean to JSON string.
     *
     * @param bean - bean to convert
     * @return JSON string
     * @throws IOException problems of serialization
     */
    public static String beanToJson(Object bean) throws IOException {
        return WRITER.writeValueAsString(bean);
    }

    /**
     * Serializes bean to JSON string in silent mode if some error occurs, defaultValue will be returned and warn logged.
     *
     * @param bean - bean to serialize
     * @return JSON string
     */
    public static String beanToJsonSilent(Object bean, String defaultValue) {
        try {
            return WRITER.writeValueAsString(bean);
        } catch (IOException e) {
            LOGGER.warn("Cant serialize bean to JSON", e);
        }
        return defaultValue;
    }
}
