package com.teamacronymcoders.packmode.condition.minecraft;

import com.teamacronymcoders.packmode.compat.Compat;
import com.teamacronymcoders.packmode.condition.minecraft.condition.PackModeConditionSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;

public class MinecraftCompat extends Compat {
    @Override
    public void setup() {
        CraftingHelper.register(new PackModeConditionSerializer());
    }
}
