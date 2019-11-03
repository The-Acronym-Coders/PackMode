package com.teamacronymcoders.packmode.compat.crafttweaker;

import com.teamacronymcoders.packmode.compat.Compat;
import crafttweaker.CraftTweakerAPI;

public class CraftTweakerCompat extends Compat {
    @Override
    public void preInit() {
        CraftTweakerAPI.tweaker.getPreprocessorManager().registerPreprocessorAction("packmode", PackModePreprocessor::new);
    }
}
