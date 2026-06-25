package io.github.thesmoothrere.relib.config.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class BooleanOptionSerializer implements ConfigOptionSerializer<Boolean> {
    @Override
    public JsonElement serialize(Boolean value) {
        return new JsonPrimitive(value);
    }

    @Override
    public Boolean deserialize(JsonElement json) {
        return json.getAsBoolean();
    }
}
