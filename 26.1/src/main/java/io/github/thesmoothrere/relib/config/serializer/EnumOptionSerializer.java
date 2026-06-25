package io.github.thesmoothrere.relib.config.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class EnumOptionSerializer<E extends Enum<E>> implements ConfigOptionSerializer<E> {
    private final Class<E> enumClass;

    public EnumOptionSerializer(Class<E> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public JsonElement serialize(E value) {
        return new JsonPrimitive(value.name());
    }

    @Override
    public E deserialize(JsonElement json) {
        return Enum.valueOf(enumClass, json.getAsString());
    }
}
