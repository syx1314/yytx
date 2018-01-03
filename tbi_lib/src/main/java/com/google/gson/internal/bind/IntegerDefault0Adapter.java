package com.google.gson.internal.bind;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

/**
 * Integer解析
 * Created by huangzhe on 2017/4/18.
 */

public class IntegerDefault0Adapter implements JsonSerializer<Integer>,JsonDeserializationContext {
    @Override
    public  Integer  deserialize(JsonElement json, Type type) throws JsonParseException {
        try {
            if (json.getAsString().equals("")){
                return 0;
            }
        } catch (Exception ignore){
        }
        try {
            return json.getAsInt();
        } catch (NumberFormatException e) {
            throw new JsonSyntaxException(e);
        }
    }

    @Override
    public JsonElement serialize(Integer src, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(src);
    }
}
