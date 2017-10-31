package io.sommers.packmode.compat.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import io.sommers.packmode.compat.Compat;

public class CraftTweakerCompat extends Compat {
    @Override
    public void preInit() {
        CraftTweakerAPI.tweaker.getPreprocessorManager().registerPreprocessorAction("packmode", PackModePreprocessor::new);
    }
}
