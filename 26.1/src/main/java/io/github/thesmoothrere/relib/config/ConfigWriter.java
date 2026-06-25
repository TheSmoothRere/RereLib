package io.github.thesmoothrere.relib.config;

import com.google.gson.*;
import io.github.thesmoothrere.relib.Constants;
import io.github.thesmoothrere.relib.config.option.ConfigOption;
import net.fabricmc.loader.api.FabricLoader;
import org.jspecify.annotations.NonNull;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class ConfigWriter {
    private final Gson gson;
    private final File configFile;
    private final Map<String, ConfigOptionAdapter<?>> options;

    public ConfigWriter(String configName) {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.configFile = FabricLoader.getInstance().getConfigDir().resolve(configName + ".json").toFile();
        this.options = new TreeMap<>();
    }

    public <T> void addOption(@NonNull ConfigOption<T> option) {
        String key = option.getKey();
        if (options.containsKey(key)) {
            Constants.LOGGER.warn("Config option {} is already registered, skipping registration", key);
            return;
        }

        options.putIfAbsent(key, new ConfigOptionAdapter<>(option));
    }

    record ConfigOptionAdapter<T>(ConfigOption<T> option) {
        void fromJson(JsonElement json) {
            option.setValue(option.getSerializer().deserialize(json));
        }

        JsonElement toJson() {
            return option.getSerializer().serialize(option.getValue());
        }
    }

    public void loadConfig() {
        if (!configFile.exists()) {
            saveConfig();
            return;
        }

        try (var reader = new FileReader(configFile)) {
            JsonElement json = gson.fromJson(reader, JsonElement.class);
            fromJson(json);
        } catch (IOException | JsonParseException e) {
            Constants.LOGGER.error("Failed to load config file: {}", configFile.getAbsoluteFile(), e);
        }
    }


    public void saveConfig() {
        try (var writer = new FileWriter(configFile)) {
            JsonElement json = toJson();
            gson.toJson(json, writer);
        } catch (JsonIOException | IOException e) {
            Constants.LOGGER.error("Failed to save config file: {}", configFile.getAbsoluteFile(), e);
        }
    }

    private void fromJson(JsonElement json) {
        options.forEach((key, adapter) -> {
            JsonElement element = json.getAsJsonObject().get(key);
            if (element != null) adapter.fromJson(element);
        });
    }

    private JsonElement toJson() {
        JsonObject jsonObject = new JsonObject();
        options.forEach((key, adapter) -> jsonObject.add(key, adapter.toJson()));
        return jsonObject;
    }
}
