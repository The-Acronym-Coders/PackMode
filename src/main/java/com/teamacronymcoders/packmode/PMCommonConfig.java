package com.teamacronymcoders.packmode;

import com.google.common.collect.Lists;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class PMCommonConfig {
    public static PMCommonConfig instance;

    public final ForgeConfigSpec.ConfigValue<String> packMode;
    public final ForgeConfigSpec.ConfigValue<List<String>> validPackModes;

    public final ForgeConfigSpec spec;

    public PMCommonConfig(ForgeConfigSpec.Builder builder) {
        packMode = builder.comment("The current Pack Mode").define("Pack Mode", "Normal");
        validPackModes = builder.comment("All valid Pack Modes").define("Valid PackModes", Lists.newArrayList("Normal", "Expert"));
        this.spec = builder.build();
    }

    public static ForgeConfigSpec initialize() {
        PMCommonConfig config = new PMCommonConfig(new ForgeConfigSpec.Builder());
        instance = config;
        return config.spec;
    }
}
