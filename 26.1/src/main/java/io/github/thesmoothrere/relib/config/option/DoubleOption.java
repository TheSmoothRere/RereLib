package io.github.thesmoothrere.relib.config.option;

import io.github.thesmoothrere.relib.config.serializer.ConfigOptionSerializer;
import io.github.thesmoothrere.relib.config.serializer.DoubleOptionSerializer;
import org.jspecify.annotations.NonNull;

public class DoubleOption extends NumberConfigOption<Double> {
    public DoubleOption(@NonNull String key, @NonNull Double defaultValue, @NonNull Double minValue, @NonNull Double maxValue) {
        super(key, defaultValue, minValue, maxValue);
    }

    @Override
    public Class<Double> getType() {
        return Double.class;
    }

    @Override
    public ConfigOptionSerializer<Double> getSerializer() {
        return new DoubleOptionSerializer();
    }
}
