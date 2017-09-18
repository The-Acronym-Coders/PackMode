package io.sommers.packmode;

import net.minecraftforge.common.config.Config;

@Config(modid = PackMode.MOD_ID)
public class PMConfig {
    public static String packMode = "normal";
    public static String[] acceptedModes = new String[] {"normal", "expert"};
}
