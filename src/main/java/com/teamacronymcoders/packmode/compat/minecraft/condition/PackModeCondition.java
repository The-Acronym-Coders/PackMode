package com.teamacronymcoders.packmode.compat.minecraft.condition;

import com.teamacronymcoders.packmode.PackMode;
import com.teamacronymcoders.packmode.api.PackModeAPI;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;

import java.util.List;

public class PackModeCondition implements ICondition {
    private final ResourceLocation id;
    private final List<String> validPackModes;

    public PackModeCondition(List<String> validPackModes) {
        this.id = new ResourceLocation(PackMode.MOD_ID, "active");
        this.validPackModes = validPackModes;
    }

    @Override
    public ResourceLocation getID() {
        return id;
    }

    @Override
    public boolean test() {
        return PackModeAPI.getInstance().includesPackMode(validPackModes);
    }

    public List<String> getValidPackModes() {
        return this.validPackModes;
    }
}
