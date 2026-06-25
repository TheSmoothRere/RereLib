package io.github.thesmoothrere.relib.config.option;

import io.github.thesmoothrere.relib.config.serializer.ConfigOptionSerializer;
import org.jspecify.annotations.NonNull;

public abstract class ConfigOption<T> {
    @NonNull
    private final String key;
    @NonNull
    private T value;
    @NonNull
    private final T defaultValue;

    protected ConfigOption(@NonNull String key, @NonNull T defaultValue) {
        this.key = key;
        this.value = defaultValue;
        this.defaultValue = defaultValue;
    }

    public @NonNull String getKey() {
        return key;
    }

    public @NonNull T getValue() {
        return value;
    }

    public void setValue(@NonNull T value) {
        this.value = value;
    }

    public @NonNull T getDefaultValue() {
        return defaultValue;
    }

    public abstract Class<T> getType();

    public abstract ConfigOptionSerializer<T> getSerializer();
}
