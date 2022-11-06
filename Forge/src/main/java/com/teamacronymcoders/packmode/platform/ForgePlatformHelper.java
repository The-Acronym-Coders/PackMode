package com.teamacronymcoders.packmode.platform;

import com.teamacronymcoders.packmode.PackModeChangedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class ForgePlatformHelper implements PlatformHelper {
    @Override
    public String getModLoader() {
        return "Forge";
    }

    @Override
    public boolean isModLoaded(String name) {
        return ModList.get().isLoaded(name);
    }

    @Override
    public boolean isProduction() {
        return FMLEnvironment.production;
    }

    @Override
    public void publishEvent(String newPackMode) {
        MinecraftForge.EVENT_BUS.post(new PackModeChangedEvent(newPackMode));
    }
}
