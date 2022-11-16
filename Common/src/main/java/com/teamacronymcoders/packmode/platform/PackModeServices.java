package com.teamacronymcoders.packmode.platform;

import com.teamacronymcoders.packmode.PackModeConstants;

import java.util.ServiceLoader;

public class PackModeServices {

    public static final PlatformHelper PLATFORM = load(PlatformHelper.class);
    public static final ConfigHelper CONFIG = load(ConfigHelper.class);

    public static <T> T load(Class<T> clazz) {

        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        PackModeConstants.LOGGER.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}
