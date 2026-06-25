package io.github.thesmoothrere.relib.config;

import java.util.ArrayList;
import java.util.List;

public record ListString(List<String> values) {
    public ListString(List<String> values) {
        this.values = new ArrayList<>(values);
    }

    public void add(String value) {
        this.values.add(value);
    }

    public void edit(String oldValue, String newValue) {
        int index = this.values.indexOf(oldValue);
        if (index != -1) this.values.set(index, newValue);
    }

    public void remove(String value) {
        this.values.remove(value);
    }

    public void clear() {
        this.values.clear();
    }
}
