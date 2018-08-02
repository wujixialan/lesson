package com.biz.lesson.util;

import javafx.beans.binding.ObjectExpression;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kevin zhao
 * @date 2018/7/28
 */
public class ReturnValueMap {
    private static Map<Object, Object> map = new HashMap<>();

    public static Map<Object, Object> success() {
        map.put("code", 200);
        return map;
    }

    public static Map<Object, Object> error() {
        map.put("code", 400);
        return map;
    }

    public ReturnValueMap addExtra(Object key, Object value) {
        this.map.put(key, value);
        return this;
    }
}
