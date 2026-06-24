package io.github.thesmoothrere.rerelib;

import io.github.thesmoothrere.rerelib.api.Config;
import io.github.thesmoothrere.rerelib.api.ConfigApi;
import io.github.thesmoothrere.rerelib.config.ConfigManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import java.util.Collection;

public class RereLib implements ModInitializer {
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
