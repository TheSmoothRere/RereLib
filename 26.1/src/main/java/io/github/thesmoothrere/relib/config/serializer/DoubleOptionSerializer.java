package io.github.thesmoothrere.relib.config.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class DoubleOptionSerializer implements ConfigOptionSerializer<Double> {
    @Override
    public JsonElement serialize(Double value) {
        return new JsonPrimitive(value);
    }

    @Override
    public Double deserialize(JsonElement json) {
        return json.getAsDouble();
    }
}
