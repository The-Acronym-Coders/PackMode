package com.teamacronymcoders.packmode;

import com.teamacronymcoders.packmode.api.PackModeAPI;
import com.teamacronymcoders.packmode.api.PackModeChangedEvent;
import net.minecraftforge.common.MinecraftForge;

import java.util.List;

public class PackModeAPIImpl extends PackModeAPI {

    @Override
    public String getPackMode() {
        return PMCommonConfig.instance.packMode.get();
    }

    @Override
    public List<String> getValidPackModes() {
        return PMCommonConfig.instance.validPackModes.get();
    }

    @Override
    public void setPackMode(String packMode) {
        PMCommonConfig.instance.packMode.set(packMode);
        MinecraftForge.EVENT_BUS.post(new PackModeChangedEvent(packMode));
    }
}
