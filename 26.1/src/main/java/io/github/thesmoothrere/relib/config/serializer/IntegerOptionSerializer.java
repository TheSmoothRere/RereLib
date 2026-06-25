package io.github.thesmoothrere.relib.config.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class IntegerOptionSerializer implements ConfigOptionSerializer<Integer> {
    @Override
    public JsonElement serialize(Integer value) {
        return new JsonPrimitive(value);
    }

    @Override
    public Integer deserialize(JsonElement json) {
        return json.getAsInt();
    }
}
