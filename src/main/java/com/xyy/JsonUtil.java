package com.xyy;

import jodd.json.JsonParser;
import jodd.json.JsonSerializer;

/**
 * <p>
 * </p>
 * User: xuyuanye Date: 2016/8/18 Project: sms-send
 */
public class JsonUtil {

    public static String serialize(Object o){
        return new JsonSerializer().serialize(o);
    }

    public static <T> T parse(String json,Class<T> type){
        return new JsonParser().parse(json,type);
    }

}
