package io.sommers.packmode.proxy;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

public class ClientProxy extends CommonProxy<EntityPlayerSP> {
    public List<EntityPlayerSP> getPlayers() {
        return Lists.newArrayList(Minecraft.getMinecraft().player);
    }
}
