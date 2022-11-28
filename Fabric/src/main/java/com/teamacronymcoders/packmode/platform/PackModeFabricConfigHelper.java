package com.teamacronymcoders.packmode.platform;

import com.teamacronymcoders.packmode.PackModeConstants;
import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraft.Util;

import java.util.ArrayList;
import java.util.List;

public class PackModeFabricConfigHelper extends MidnightConfig implements ConfigHelper {

    @Comment
    public static Comment title;

    @Comment
    public static Comment currentPackMode;

    @Entry
    public static String packMode = "Normal";

    @Comment
    public static Comment possiblePackModes;

    @Entry
    public static List<String> packModes = Util.make(() -> {
        List<String> temp = new ArrayList<>();
        temp.add("Normal");
        temp.add("Expert");
        return temp;
    });


    @Override
    public String getPackMode() {
        return packMode;
    }

    @Override
    public void setPackMode(String packMode) {
        PackModeFabricConfigHelper.packMode = packMode;
        write(PackModeConstants.MODID);
    }

    @Override
    public List<String> getValidPackModes() {
        return packModes;
    }
}
