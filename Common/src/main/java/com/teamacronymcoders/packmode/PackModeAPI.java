package com.teamacronymcoders.packmode;

import java.util.List;
import java.util.Objects;

public abstract class PackModeAPI {

    //Should this be thread safe now that the api is resolved before FMLSetupCommonEvent on Forge?

    private static PackModeAPI instance;

    public static PackModeAPI getInstance() {
        return Objects.requireNonNull(instance, "PackMode API not created yet!");
    }

    public static void setInstance(PackModeAPI packModeAPI) {
        if (Objects.isNull(instance)) {
            instance = packModeAPI;
        } else {
            throw new IllegalStateException("Tried to set PackMode API more than once");
        }
    }

    public abstract String getPackMode();

    public abstract List<String> getValidPackModes();

    public boolean isValidPackMode(String packMode) {
        return this.getValidPackModes().contains(packMode);
    }

    public boolean includesPackMode(List<String> packModes) {
        return packModes.stream().anyMatch(this.getPackMode()::equalsIgnoreCase);
    }

    abstract void setPackMode(String packMode);
}
