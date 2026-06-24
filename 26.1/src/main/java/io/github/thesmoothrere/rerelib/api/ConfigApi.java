package io.github.thesmoothrere.rerelib.api;

public interface ConfigApi {
    default String getConfigName() {
        return getAnnotation().name();
    }

    default Config getAnnotation() {
        Class<? extends ConfigApi> clazz = this.getClass();
        Config annotation = clazz.getAnnotation(Config.class);
        if (annotation == null)
            throw new IllegalStateException("Config class " + clazz.getName() + " is missing @Config annotation");
        return annotation;
    }
}
