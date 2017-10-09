package io.sommers.packmode;

import net.minecraftforge.common.config.Config;

@Config(modid = PackMode.MOD_ID)
public class PMConfig {
    static String packMode = "normal";
    static String[] acceptedModes = new String[] {"normal", "expert"};
}
