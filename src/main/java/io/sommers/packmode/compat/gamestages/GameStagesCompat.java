package io.sommers.packmode.compat.gamestages;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.sommers.packmode.PMConfig;
import io.sommers.packmode.PackMode;
import io.sommers.packmode.api.PackModeAPI;
import io.sommers.packmode.api.PackModeChangedEvent;
import io.sommers.packmode.compat.Compat;
import joptsimple.internal.Strings;
import net.darkhax.gamestages.GameStages;
import net.darkhax.gamestages.capabilities.PlayerDataHandler;
import net.darkhax.gamestages.capabilities.PlayerDataHandler.IStageData;
import net.darkhax.gamestages.packet.PacketStage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.util.List;
import java.util.Map;

public class GameStagesCompat extends Compat {
    private Map<String, String[]> packModeToGameStages;

    public GameStagesCompat() {
        packModeToGameStages = Maps.newHashMap();
    }

    @Override
    public void preInit() {
        MinecraftForge.EVENT_BUS.register(this);
        Configuration configuration = PMConfig.getConfiguration();

        ConfigCategory gameStagesCategory = configuration.getCategory("GameStages Mappings");
        gameStagesCategory.setComment("The GameStages that will get added when a PackMode is set. (Happens Immediately with no Restart");
        String[] packModes = PMConfig.getAcceptedModes();
        for (String packMode : packModes) {
            Property gameStageMapping = configuration.get(gameStagesCategory.getName(), packMode, new String[0]);
            packModeToGameStages.put(packMode, gameStageMapping.getStringList());
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void gameStageChangeOnPackMode(PackModeChangedEvent event) {
        List<EntityPlayer> entityPlayerList = PackMode.proxy.getPlayers();
        for (EntityPlayer entityPlayer : entityPlayerList) {
            handleGameStages(entityPlayer, event.getNextRestartPackMode(), true);
        }
    }

    @SubscribeEvent
    public void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        handleGameStages(event.player, PackModeAPI.getInstance().getNextRestartPackMode(), false);
    }

    public void handleGameStages(EntityPlayer entityPlayer, String packMode, boolean sendMessage) {
        IStageData stageData = PlayerDataHandler.getStageData(entityPlayer);
        List<String> addedStages = Lists.newArrayList();
        List<String> removedStages = Lists.newArrayList();
        for (Map.Entry<String, String[]> entry : packModeToGameStages.entrySet()) {
            for (String gameStage : entry.getValue()) {
                if (entry.getKey().equals(packMode)) {
                    if (!stageData.hasUnlockedStage(gameStage)) {
                        stageData.unlockStage(gameStage);
                        addedStages.add(gameStage);
                        if (entityPlayer instanceof EntityPlayerMP) {
                            GameStages.NETWORK.sendTo(new PacketStage(gameStage, true), (EntityPlayerMP) entityPlayer);
                        }
                    }
                } else {
                    if (stageData.hasUnlockedStage(gameStage)) {
                        stageData.lockStage(gameStage);
                        removedStages.add(gameStage);
                        if (entityPlayer instanceof EntityPlayerMP) {
                            GameStages.NETWORK.sendTo(new PacketStage(gameStage, false), (EntityPlayerMP) entityPlayer);
                        }
                    }
                }
            }
        }
        if (sendMessage) {
            if (!addedStages.isEmpty()) {
                entityPlayer.sendStatusMessage(new TextComponentString("GameStages added: " + Strings.join(addedStages, ",")), true);
            }
            if (!removedStages.isEmpty()) {
                entityPlayer.sendStatusMessage(new TextComponentString("GameStages removed: " + Strings.join(removedStages, ",")), true);

            }
        }
    }
}
