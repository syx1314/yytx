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
 * Created by huangzhe on 2017/9/28.
 */

public class LongDefaultAdapter implements JsonSerializer<Long>,JsonDeserializationContext {
    @Override
    public  Long  deserialize(JsonElement json, Type type) throws JsonParseException {
        try {
            if (json.getAsString().equals("")){
                return 0l;
            }
        } catch (Exception ignore){
        }
        try {
            return json.getAsLong();
        } catch (NumberFormatException e) {
            throw new JsonSyntaxException(e);
        }
    }

    @Override
    public JsonElement serialize(Long src, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(src);
    }
}
