package io.sommers.packmode;

import com.google.common.collect.Lists;
import io.sommers.packmode.api.PackModeAPI;
import io.sommers.packmode.api.PackModeChangedEvent;
import io.sommers.packmode.compat.CompatHandler;
import io.sommers.packmode.proxy.CommonProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;

import static io.sommers.packmode.PackMode.*;

@Mod(modid = MOD_ID, name = MOD_NAME, version = VERSION, dependencies = DEPENDS, acceptedMinecraftVersions = MC_VERSIONS)
public class PackMode {
    public static final String MOD_ID = "packmode";
    public static final String MOD_NAME = "PackMode";
    public static final String VERSION = "@VERSION@";
    public static final String DEPENDS = "after:crafttweaker;after:gamestages";
    public static final String MC_VERSIONS = "[1.12, 1.13)";

    public static Logger logger;

    @SidedProxy(clientSide = "io.sommers.packmode.proxy.CommonProxy",
            serverSide = "ios.sommers.packmode.proxy.ServerProxy")
    public static CommonProxy<EntityPlayer> proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        PMConfig.init(event.getSuggestedConfigurationFile());
        logger = event.getModLog();
        PackModeAPI.createInstance(PMConfig.getPackMode(), Lists.newArrayList(PMConfig.getAcceptedModes()));

        CompatHandler.tryActivate();
        CompatHandler.preInit();

        MinecraftForge.EVENT_BUS.register(this);
    }

    @EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new PackModeCommand());
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onPackModeChanged(PackModeChangedEvent event) {
        PMConfig.setPackMode(event.getNextRestartPackMode());
    }
}
