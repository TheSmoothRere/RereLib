package io.github.thesmoothrere.relib.config;

import io.github.thesmoothrere.relib.Constants;
import io.github.thesmoothrere.relib.api.ConfigApi;
import io.github.thesmoothrere.relib.config.option.ConfigOption;
import org.jspecify.annotations.NonNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class ConfigManager {
    private static final Map<Class<? extends ConfigApi>, ConfigApi> CONFIGS = new HashMap<>();

    private ConfigManager() {
        /* This utility class should not be instantiated */
    }

    public static <T extends ConfigApi> void register(T configInstance) {
        Class<? extends ConfigApi> configClass = configInstance.getClass();
        if (CONFIGS.containsKey(configClass)) {
            Constants.LOGGER.warn("Config class {} is already registered, skipping registration", configClass);
            return;
        }

        load(configInstance);
        CONFIGS.putIfAbsent(configClass, configInstance);
        Constants.LOGGER.info("Registered config class {}", configClass);
    }

    public static void load(ConfigApi config) {
        getWriter(config).loadConfig();
    }

    public static void save(ConfigApi config) {
        getWriter(config).saveConfig();
    }

    private static @NonNull ConfigWriter getWriter(ConfigApi config) {
        ConfigWriter writer = new ConfigWriter(config.getConfigName());
        fillWriter(config, writer);
        return writer;
    }

    // Helper to extract options from the config object
    private static void fillWriter(ConfigApi config, ConfigWriter writer) {
        Arrays.stream(config.getClass().getMethods())
                .filter(m -> ConfigOption.class.isAssignableFrom(m.getReturnType()))
                .filter(m -> m.getParameterCount() == 0)
                .forEach(m -> {
                    try {
                        Object value = m.invoke(config);
                        if (value instanceof ConfigOption<?> option) {
                            writer.addOption(option);
                        }
                    } catch (Exception e) {
                        Constants.LOGGER.error("Failed to read option: {}", m.getName(), e);
                    }
                });
    }

    @SuppressWarnings("unchecked")
    public static <T extends ConfigApi> T get(Class<T> configClass) {
        ConfigApi config = CONFIGS.get(configClass);
        if (config == null) {
            throw new IllegalArgumentException("No registered config found for class: " + configClass.getName());
        }
        return (T) config;
    }
}
