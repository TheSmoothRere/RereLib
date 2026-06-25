package io.github.thesmoothrere.relib.config.serializer;

import com.google.gson.JsonElement;

public interface ConfigOptionSerializer<T> {
    JsonElement serialize(T value);
    T deserialize(JsonElement json);
}
