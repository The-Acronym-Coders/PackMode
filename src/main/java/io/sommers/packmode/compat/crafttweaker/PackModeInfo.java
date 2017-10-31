package io.sommers.packmode.compat.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import io.sommers.packmode.api.PackModeAPI;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.packmode.PackModeInfo")
public class PackModeInfo {
    @ZenMethod
    public String getPackMode() {
        return PackModeAPI.getInstance().getCurrentPackMode();
    }

    @ZenMethod
    public boolean matchPackMode(String packmode) {
        return PackModeAPI.getInstance().getCurrentPackMode().equals(packmode);
    }

    @ZenMethod
    public String[] getAcceptablePackModes() {
        return PackModeAPI.getInstance().getPackModes().toArray(new String[0]);
    }
}
