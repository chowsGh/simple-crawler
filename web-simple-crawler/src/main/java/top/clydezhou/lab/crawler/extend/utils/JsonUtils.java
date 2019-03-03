package top.clydezhou.lab.crawler.extend.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtils {
    private static ObjectMapper om = new ObjectMapper();
    static {
        om.setSerializationInclusion(Include.NON_EMPTY);
    }
    public static String getJson(Object obj) {
        String json = null;
        try {
            json = om.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
             log.error("getJson exception", e);
        }
        return json;
    }
    public static <T> T readValue(String content, Class<T> valueType) 
    {
        T value = null;
        try {
            value = om.readValue(content, valueType);
        } catch (Exception e) {
            log.error("readValue", e);
        }
        return value;
    }
}
