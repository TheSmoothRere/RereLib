package io.github.thesmoothrere.relib.config.option;

import io.github.thesmoothrere.relib.config.serializer.ConfigOptionSerializer;
import io.github.thesmoothrere.relib.config.serializer.StringOptionSerializer;
import org.jspecify.annotations.NonNull;

public class StringOption extends ConfigOption<String> {
    public StringOption(@NonNull String key, @NonNull String defaultValue) {
        super(key, defaultValue);
    }

    @Override
    public Class<String> getType() {
        return String.class;
    }

    @Override
    public ConfigOptionSerializer<String> getSerializer() {
        return new StringOptionSerializer();
    }
}
