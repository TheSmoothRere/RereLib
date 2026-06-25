package io.github.thesmoothrere.relib.config.option;

import org.jspecify.annotations.NonNull;

public abstract class NumberConfigOption<T extends Number & Comparable<T>> extends ConfigOption<T> {
    @NonNull
    private final T minValue;
    @NonNull
    private final T maxValue;

    protected NumberConfigOption(@NonNull String key, @NonNull T defaultValue, @NonNull T minValue, @NonNull T maxValue) {
        validateBounds(defaultValue, minValue, maxValue);
        super(key, defaultValue);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    private static <T extends Number & Comparable<T>> void validateBounds(T defaultValue, T minValue, T maxValue) {
        if (minValue.compareTo(maxValue) > 0) {
            throw new IllegalArgumentException("Min value cannot be greater than max value!");
        }
        if (defaultValue.compareTo(minValue) < 0) {
            throw new IllegalArgumentException("Default value cannot be less than min value!");
        }
        if (defaultValue.compareTo(maxValue) > 0) {
            throw new IllegalArgumentException("Default value cannot be greater than max value!");
        }
    }

    public @NonNull T getMinValue() {
        return minValue;
    }

    public @NonNull T getMaxValue() {
        return maxValue;
    }
}
