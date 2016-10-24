package com.utn.tesis.util;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Maxi
 * Date: 23/10/16
 * Time: 18:34
 * To change this template use File | Settings | File Templates.
 */
public class JacksonUtil {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> T fromString(String string, Class<T> clazz) {
        if(string == null || clazz == null) return null;
        try {
            return OBJECT_MAPPER.readValue(string, clazz);
        } catch (IOException e) {
            throw new IllegalArgumentException("The given string value: " + string + " cannot be transformed to Json object");
        }
    }

    public static String toString(Object value) {
        if(value == null) return null;
        try {
            return OBJECT_MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("The given Json object value: " + value + " cannot be transformed to a String");
        } catch (IOException e) {
            throw new IllegalArgumentException("IOExeption The given Json object value: " + value + " cannot be transformed to a String");
        }
    }

    public static JsonNode toJsonNode(String value) {
        if(value == null) return null;
        try {
            return OBJECT_MAPPER.readTree(value);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static <T> T clone(T value) {
        if(value == null) return null;
        return fromString(toString(value), (Class<T>) value.getClass());
    }
}
