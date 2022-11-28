package com.teamacronymcoders.packmode.platform;

import com.google.common.collect.Lists;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class PackModeForgeConfigHelper implements ConfigHelper {

    public static ForgeConfigSpec.ConfigValue<String> packMode;
    public static ForgeConfigSpec.ConfigValue<List<String>> validPackModes;


    public static ForgeConfigSpec makeConfig(ForgeConfigSpec.Builder builder) {
        packMode = builder.comment("The current Pack Mode").define("Pack Mode", "Normal");
        validPackModes = builder.comment("All valid Pack Modes").define("Valid PackModes", Lists.newArrayList("Normal", "Expert"));
        return builder.build();
    }

    @Override
    public String getPackMode() {
        return packMode.get();
    }

    @Override
    public void setPackMode(String packMode) {
        PackModeForgeConfigHelper.packMode.set(packMode);
        PackModeForgeConfigHelper.packMode.save();
    }

    @Override
    public List<String> getValidPackModes() {
        return validPackModes.get();
    }
}
