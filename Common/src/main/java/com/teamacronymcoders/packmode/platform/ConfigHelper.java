package com.teamacronymcoders.packmode.platform;

import java.util.List;

public interface ConfigHelper {

    String getPackMode();
    void setPackMode(String packMode);
    List<String> getValidPackModes();
}
