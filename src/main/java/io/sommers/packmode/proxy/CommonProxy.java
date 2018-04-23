package io.sommers.packmode.proxy;

import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

public class CommonProxy<T extends EntityPlayer> {
    public List<T> getPlayers() {
        return Lists.newArrayList();
    }
}
