package com.teamacronymcoders.packmode.platform;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.loader.api.FabricLoader;

public class FabricPlatformHelper implements PlatformHelper {

    //Empty consumer as our Event is purely informative.
    public static final Event<DummyInterface<PackModeChangedEvent>> PACK_MODE_CHANGED_EVENT =
            EventFactory.createArrayBacked(DummyInterface.class, listeners -> packModeChangedEvent -> {});

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
        PackModeChangedEvent event = new PackModeChangedEvent(newPackMode);
        if (isModLoaded("fabric")) {
            PACK_MODE_CHANGED_EVENT.invoker().handle(event);
        }
    }

    private interface DummyInterface<U> {
        void handle(U u);
    }
}