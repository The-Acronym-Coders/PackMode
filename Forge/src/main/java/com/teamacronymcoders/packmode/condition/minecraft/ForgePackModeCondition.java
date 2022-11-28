package com.teamacronymcoders.packmode.condition.minecraft;

import com.teamacronymcoders.packmode.PackModeAPI;
import com.teamacronymcoders.packmode.compat.minecraft.PackModeCondition;
import net.minecraftforge.common.crafting.conditions.ICondition;

import java.util.List;

public class ForgePackModeCondition extends PackModeCondition implements ICondition {

    public ForgePackModeCondition(List<String> validPackModes) {
        super(validPackModes);
    }

    //Call on forge
    public boolean test(IContext context) {
        return PackModeAPI.getInstance().includesPackMode(this.getValidPackModes());
    }

}
