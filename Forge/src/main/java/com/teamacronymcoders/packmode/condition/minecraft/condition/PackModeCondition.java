package com.teamacronymcoders.packmode.condition.minecraft.condition;

import com.teamacronymcoders.packmode.PackModeConstants;
import com.teamacronymcoders.packmode.api.PackModeAPI;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;

import java.util.List;

public class PackModeCondition implements ICondition {

    private static final ResourceLocation id = new ResourceLocation(PackModeConstants.MODID, "active");
    private final List<String> validPackModes;

    public PackModeCondition(List<String> validPackModes) {
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
