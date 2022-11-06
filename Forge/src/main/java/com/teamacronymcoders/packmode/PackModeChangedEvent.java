package com.teamacronymcoders.packmode;

import net.minecraftforge.eventbus.api.Event;

public class PackModeChangedEvent extends Event {
    private final String newPackMode;

    public PackModeChangedEvent(String newPackMode) {
        this.newPackMode = newPackMode;
    }

    public String getNewPackMode() {
        return this.newPackMode;
    }
}
