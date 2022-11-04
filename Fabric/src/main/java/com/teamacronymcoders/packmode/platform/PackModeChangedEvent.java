package com.teamacronymcoders.packmode.platform;

import net.fabricmc.fabric.api.event.Event;

public class PackModeChangedEvent extends Event {

    private final String newPackMode;

    public PackModeChangedEvent(String newPackMode) {
        this.newPackMode = newPackMode;
    }

    public String getNewPackMode() {
        return newPackMode;
    }

    @Override
    public void register(Object listener) {

    }
}
