package io.github.thesmoothrere.relib;

import io.github.thesmoothrere.relib.api.Config;
import io.github.thesmoothrere.relib.api.ConfigApi;
import io.github.thesmoothrere.relib.config.ConfigManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import java.util.Collection;

public class ReLib implements ModInitializer {
    @Override
    public void onInitialize() {
        Constants.LOGGER.info("Hello Fabric world!");

        Collection<ConfigApi> configs = FabricLoader.getInstance().getEntrypoints(Constants.MOD_ID, ConfigApi.class);
        configs.forEach(config -> {
            Class<? extends ConfigApi> configClass = config.getClass();
            if (configClass.isAnnotationPresent(Config.class)) {
                ConfigManager.register(config);
            } else {
                Constants.LOGGER.error("Config class {} is missing @Config annotation", configClass.getName());
            }
        });
    }
}
