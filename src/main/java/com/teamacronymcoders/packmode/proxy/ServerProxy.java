package com.teamacronymcoders.packmode.proxy;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.List;

public class ServerProxy extends CommonProxy<EntityPlayerMP> {
    @Override
    public List<EntityPlayerMP> getPlayers() {
        return FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers();
    }
}
