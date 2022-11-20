package com.teamacronymcoders.packmode.platform;

public interface PlatformHelper {

    String getModLoader();
    boolean isModLoaded(String name);
    boolean isProduction();
    void publishEvent(String newPackMode);
    void setupJsonConditions();
}
