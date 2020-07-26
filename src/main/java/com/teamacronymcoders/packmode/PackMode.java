package com.teamacronymcoders.packmode;

import com.teamacronymcoders.packmode.api.PackModeAPI;
import com.teamacronymcoders.packmode.compat.CompatHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.teamacronymcoders.packmode.PackMode.*;

@Mod(value = MOD_ID)
public class PackMode {
    public static final String MOD_ID = "packmode";

    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public PackMode() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.addListener(this::onServerStarting);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, PMCommonConfig.initialize());
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
