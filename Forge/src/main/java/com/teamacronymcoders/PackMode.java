package com.teamacronymcoders;

import com.teamacronymcoders.packmode.PackModeAPIImpl;
import com.teamacronymcoders.packmode.PackModeCommand;
import com.teamacronymcoders.packmode.PackModeConstants;
import com.teamacronymcoders.packmode.api.PackModeAPI;
import com.teamacronymcoders.packmode.compat.CompatHandler;
import com.teamacronymcoders.packmode.platform.ForgeConfigHelper;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(value = PackModeConstants.MODID)
public class PackMode {

    public PackMode() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.addListener(this::onServerStarting);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ForgeConfigHelper.makeConfig(new ForgeConfigSpec.Builder()));
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        PackModeAPI.setInstance(new PackModeAPIImpl());
        CompatHandler.tryActivate();
        CompatHandler.setup();


    }

    private void onServerStarting(RegisterCommandsEvent event) {
        event.getDispatcher().register(PackModeCommand.create());
    }
}
