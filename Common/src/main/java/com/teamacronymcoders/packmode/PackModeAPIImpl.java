package com.teamacronymcoders.packmode;

import com.teamacronymcoders.packmode.api.PackModeAPI;
import com.teamacronymcoders.packmode.platform.PackModeServices;

import java.util.List;

public class PackModeAPIImpl extends PackModeAPI {

    @Override
    public String getPackMode() {
        return PackModeServices.CONFIG.getPackMode();
    }

    @Override
    public List<String> getValidPackModes() {
        return PackModeServices.CONFIG.getValidPackModes();
    }

    @Override
    public void setPackMode(String packMode) {
        PackModeServices.CONFIG.setPackMode(packMode);
        PackModeServices.PLATFORM.publishEvent(packMode);
    }
}
