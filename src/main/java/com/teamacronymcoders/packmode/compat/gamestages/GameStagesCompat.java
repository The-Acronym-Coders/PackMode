package com.teamacronymcoders.packmode.compat.gamestages;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.teamacronymcoders.packmode.compat.Compat;
import com.teamacronymcoders.packmode.PMConfig;
import com.teamacronymcoders.packmode.PackMode;
import com.teamacronymcoders.packmode.api.PackModeAPI;
import com.teamacronymcoders.packmode.api.PackModeChangedEvent;
import joptsimple.internal.Strings;
import net.darkhax.gamestages.GameStageHelper;
import net.darkhax.gamestages.data.IStageData;
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
        PackMode.proxy.getPlayers().parallelStream()
                .filter(entityPlayer -> entityPlayer instanceof EntityPlayerMP)
                .map(entityPlayer -> (EntityPlayerMP) entityPlayer)
                .forEach(entityPlayerMP -> handleGameStages(entityPlayerMP, event.getNextRestartPackMode(), true));
    }

    @SubscribeEvent
    public void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.player instanceof EntityPlayerMP) {
            handleGameStages((EntityPlayerMP) event.player, PackModeAPI.getInstance().getNextRestartPackMode(), false);
        }
    }

    public void handleGameStages(EntityPlayerMP entityPlayer, String packMode, boolean sendMessage) {
        IStageData stageData = GameStageHelper.getPlayerData(entityPlayer);
        List<String> addedStages = Lists.newArrayList();
        List<String> removedStages = Lists.newArrayList();
        for (Map.Entry<String, String[]> entry : packModeToGameStages.entrySet()) {
            for (String gameStage : entry.getValue()) {
                if (entry.getKey().equals(packMode)) {
                    if (!stageData.hasStage(gameStage)) {
                        stageData.addStage(gameStage);
                        addedStages.add(gameStage);
                    }
                } else {
                    if (stageData.hasStage(gameStage)) {
                        stageData.removeStage(gameStage);
                        removedStages.add(gameStage);
                    }
                }
            }
        }

        if (!addedStages.isEmpty() || !removedStages.isEmpty()) {
            GameStageHelper.syncPlayer(entityPlayer);
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
