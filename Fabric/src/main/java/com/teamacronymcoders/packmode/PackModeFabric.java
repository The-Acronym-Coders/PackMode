package com.teamacronymcoders.packmode;

import com.teamacronymcoders.packmode.api.PackModeAPI;
import com.teamacronymcoders.packmode.compat.CompatHandler;
import com.teamacronymcoders.packmode.compat.minecraft.MinecraftCompat;
import com.teamacronymcoders.packmode.platform.FabricConfigHelper;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class PackModeFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        MidnightConfig.init(PackModeConstants.MODID, FabricConfigHelper.class);
        PackModeAPI.setInstance(new PackModeAPIImpl());
        CompatHandler.registerCompat("minecraft", MinecraftCompat.class);
        CompatHandler.tryActivate();
        CompatHandler.setup();

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(PackModeCommand.create()));

    }

}
