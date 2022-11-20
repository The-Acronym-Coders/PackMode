package com.teamacronymcoders.packmode.compat.minecraft;

import com.teamacronymcoders.packmode.compat.Compat;
import com.teamacronymcoders.packmode.platform.PackModeServices;



public class MinecraftCompat extends Compat {

    @Override
    public void setup() {
        PackModeServices.PLATFORM.setupJsonConditions();
    }

}
