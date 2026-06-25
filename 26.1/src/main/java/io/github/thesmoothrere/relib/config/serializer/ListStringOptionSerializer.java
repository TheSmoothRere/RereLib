package io.github.thesmoothrere.relib.config.serializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import io.github.thesmoothrere.relib.config.ListString;

import java.util.ArrayList;
import java.util.List;

public class ListStringOptionSerializer implements ConfigOptionSerializer<ListString> {
    @Override
    public JsonElement serialize(ListString value) {
        JsonArray array = new JsonArray();
        for (String s : value.values()) {
            array.add(new JsonPrimitive(s));
        }
        return array;
    }

    @Override
    public ListString deserialize(JsonElement json) {
        JsonArray array = json.getAsJsonArray();
        List<String> values = new ArrayList<>();
        for (JsonElement element : array) {
            values.add(element.getAsString());
        }
        return new ListString(values);
    }
}
