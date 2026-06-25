package io.github.thesmoothrere.relib.config.option;

import io.github.thesmoothrere.relib.config.serializer.ConfigOptionSerializer;
import io.github.thesmoothrere.relib.config.serializer.IntegerOptionSerializer;
import org.jspecify.annotations.NonNull;

public class IntegerOption extends NumberConfigOption<Integer> {
    public IntegerOption(@NonNull String key, @NonNull Integer defaultValue, @NonNull Integer minValue, @NonNull Integer maxValue) {
        super(key, defaultValue, minValue, maxValue);
    }

    @Override
    public Class<Integer> getType() {
        return Integer.class;
    }

    @Override
    public ConfigOptionSerializer<Integer> getSerializer() {
        return new IntegerOptionSerializer();
    }
}
