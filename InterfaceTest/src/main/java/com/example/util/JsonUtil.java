package com.example.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * JSON转换工具类
 */
public class JsonUtil {
    public static JSONObject getJsonObject(String s) {
        if (!StringUtils.isEmpty(s)) {
            return JSONObject.parseObject(s);
        }
        return null;
    }

    public static Map<String, String> getJsonMap(String s) {
        if (!StringUtils.isEmpty(s)) {
            return JSONObject.parseObject(s, Map.class);
        }
        return null;
    }
}
