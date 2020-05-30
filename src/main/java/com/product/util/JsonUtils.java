package com.product.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonUtils {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    private static ObjectMapper mapper;
    private static ObjectMapper policyMapper;

    private JsonUtils() {
    }

    public static void initJson() {
        mapper = new ObjectMapper();

    }

    public static void initMapper() {
        policyMapper = new ObjectMapper();
        policyMapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);
        policyMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        policyMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> T toSnakeObject(String json, Class<T> clazz) throws IOException {
        if (json == null) {
            return null;
        }
        try {
            if (null == policyMapper)
                initMapper();
            return (T) policyMapper.readValue(json, clazz);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 
     * @Title: transFromJson
     * @Description: no any policy ,only for json transition.
     * @param json
     * @param tr
     * @return
     * @return: T
     */
    public static <T> T transFromJson(String json, TypeReference<T> tr) {
        if (json == null) {
            return null;
        }
        if (mapper == null) {
            initJson();
        }
        try {
            return (T) mapper.readValue(json, tr);
        } catch (Exception e) {

            logger.debug("json error:" + e);
        }
        return null;
    }

    /**
     * 
     * @Title: transToJson
     * @Description: no any policy ,only for json transition.
     * @param object
     * @return
     * @return: String
     */
    public static <T> String transToJson(T object) {
        if (object == null) {
            return null;
        }
        if (mapper == null) {
            initJson();
        }
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 
     * @Title: transToJson
     * @Description: return SNAKE_CASE, include NON_NULL
     * @param object
     * @return
     * @return: return a string, if the object is null return ""
     */
    public static <T> String translateToJson(T object) {
        String result = "";
        if (null == object)
            return result;
        try {
            if (null == policyMapper)
                initMapper();
            result = policyMapper.writeValueAsString(object);
        } catch (Exception e) {
            result = "Failed to translate Object:" + object.toString();
        }
        return result;
    }

}
