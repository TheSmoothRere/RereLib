package io.github.thesmoothrere.relib.config.option;

import io.github.thesmoothrere.relib.config.ListString;
import io.github.thesmoothrere.relib.config.serializer.ConfigOptionSerializer;
import io.github.thesmoothrere.relib.config.serializer.ListStringOptionSerializer;
import org.jspecify.annotations.NonNull;

public class ListStringOption extends ConfigOption<ListString> {
    public ListStringOption(@NonNull String key, @NonNull ListString defaultValue) {
        super(key, defaultValue);
    }

    @Override
    public Class<ListString> getType() {
        return ListString.class;
    }

    @Override
    public ConfigOptionSerializer<ListString> getSerializer() {
        return new ListStringOptionSerializer();
    }
}
