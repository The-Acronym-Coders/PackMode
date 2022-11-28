package com.teamacronymcoders.packmode.platform;

import com.teamacronymcoders.packmode.compat.minecraft.PackModeCondition;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.fabricmc.loader.api.FabricLoader;

public class PackModeFabricPlatformHelper implements PlatformHelper {

    //Empty consumer as our Event is purely informative.
    public static final Event<PackModeChanged> PACK_MODE_CHANGED_EVENT =
            EventFactory.createArrayBacked(PackModeChanged.class, listeners -> newPackModeValue -> {
                for (PackModeChanged eventListener : listeners) {
                    eventListener.handle(newPackModeValue);
                }
            });

    @Override
    public String getModLoader() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String name) {
        return FabricLoader.getInstance().isModLoaded(name);
    }

    @Override
    public boolean isProduction() {
        return !FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public void publishEvent(String newPackMode) {
        if (isModLoaded("fabric")) {
            PACK_MODE_CHANGED_EVENT.invoker().handle(newPackMode);
        }
    }

    public interface PackModeChanged {
        void handle(String newPackMode);
    }

    @Override
    public void setupJsonConditions() {
        ResourceConditions.register(PackModeCondition.id, PackModeCondition::test);
    }
}
