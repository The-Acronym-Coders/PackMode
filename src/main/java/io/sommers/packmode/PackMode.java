package io.sommers.packmode;

import com.google.common.collect.Lists;
import crafttweaker.CraftTweakerAPI;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = PackMode.MOD_ID, name = PackMode.MOD_NAME, version = PackMode.VERSION, dependencies = PackMode.DEPENDS)
public class PackMode {
    public static final String MOD_ID = "packmode";
    public static final String MOD_NAME = "PackMode";
    public static final String VERSION = "1.0.0";
    public static final String DEPENDS = "required-after:crafttweaker";

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        if (!Lists.newArrayList(PMConfig.acceptedModes).contains(PMConfig.packMode)) {
            throw new RuntimeException("Current Pack Mode not found in Accepted Modes");
        }
        CraftTweakerAPI.tweaker.getPreprocessorManager().registerPreprocessorAction("packmode", PackModePreprocessor::new);
    }

    @EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new PackModeCommand());
    }
}
