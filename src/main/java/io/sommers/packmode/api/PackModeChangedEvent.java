package io.sommers.packmode.api;

import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class PackModeChangedEvent extends Event {
    private final String nextRestartPackMode;

    PackModeChangedEvent(String nextRestartPackMode) {
        this.nextRestartPackMode = nextRestartPackMode;
    }

    public String getNextRestartPackMode() {
        return this.nextRestartPackMode;
    }
}
