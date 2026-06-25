package io.github.thesmoothrere.relib.config.option;

import io.github.thesmoothrere.relib.config.serializer.BooleanOptionSerializer;
import io.github.thesmoothrere.relib.config.serializer.ConfigOptionSerializer;
import org.jspecify.annotations.NonNull;

public class BooleanOption extends ConfigOption<Boolean> {
    public BooleanOption(@NonNull String key, @NonNull Boolean defaultValue) {
        super(key, defaultValue);
    }

    @Override
    public Class<Boolean> getType() {
        return Boolean.class;
    }

    @Override
    public ConfigOptionSerializer<Boolean> getSerializer() {
        return new BooleanOptionSerializer();
    }
}
