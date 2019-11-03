package com.teamacronymcoders.packmode.compat.minecraft;

import com.teamacronymcoders.packmode.compat.Compat;
import com.teamacronymcoders.packmode.compat.minecraft.condition.PackModeConditionSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;

public class MineCraftCompat extends Compat {
    @Override
    public void setup() {
        CraftingHelper.register(new PackModeConditionSerializer());
    }
}
