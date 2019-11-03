package com.teamacronymcoders.packmode.api;

import com.google.common.collect.ImmutableList;
import joptsimple.internal.Strings;
import net.minecraftforge.common.MinecraftForge;
import scala.actors.threadpool.Arrays;

import java.util.List;
import java.util.Objects;

public class PackModeAPI {
    private static PackModeAPI instance;

    private final String currentPackMode;
    private final List<String> packModes;

    private String nextRestartPackMode;

    private PackModeAPI(String currentPackMode, List<String> packModes) {
        this.currentPackMode = currentPackMode;
        this.nextRestartPackMode = currentPackMode;

        this.packModes = ImmutableList.copyOf(packModes);
    }

    public static PackModeAPI getInstance() {
        return Objects.requireNonNull(instance, "PackMode API not created yet!");
    }

    public static void createInstance(String currentPackMode, List<String> packModes) {
        if (instance != null) {
            throw new IllegalStateException("PackMode API was created more than once!");
        } else if (!packModes.contains(currentPackMode)) {
            throw new IllegalArgumentException("Pack Mode " + currentPackMode + " is not contained in " +
                    Strings.join(packModes, " , "));
        }
        instance = new PackModeAPI(currentPackMode, packModes);
    }

    public String getCurrentPackMode() {
        return this.currentPackMode;
    }

    public List<String> getPackModes() {
        return this.packModes;
    }

    public String getNextRestartPackMode() {
        return this.nextRestartPackMode;
    }

    public boolean isValidPackMode(String packMode) {
        return this.packModes.contains(packMode);
    }

    public void setNextRestartPackMode(String nextRestartPackMode) {
        if (this.isValidPackMode(nextRestartPackMode)) {
            this.nextRestartPackMode = nextRestartPackMode;
            MinecraftForge.EVENT_BUS.post(new PackModeChangedEvent(nextRestartPackMode));
        } else {
            throw new IllegalArgumentException("Pack Mode " + nextRestartPackMode + " is not contained in " +
                    Strings.join(this.getPackModes(), " , "));
        }

    }
}
