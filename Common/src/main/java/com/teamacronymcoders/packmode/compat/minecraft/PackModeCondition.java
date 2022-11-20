package com.teamacronymcoders.packmode.compat.minecraft;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.teamacronymcoders.packmode.PackModeConstants;
import com.teamacronymcoders.packmode.api.PackModeAPI;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public abstract class PackModeCondition {

    public static final ResourceLocation id = new ResourceLocation(PackModeConstants.MODID, "active");
    private final List<String> validPackModes;

    public PackModeCondition(List<String> validPackModes) {
        this.validPackModes = validPackModes;
    }

    public ResourceLocation getID() {
        return id;
    }

    //Call on fabric
    public static boolean test(JsonObject json) {
        JsonElement value = json.get("value");

        if (value == null) {
            PackModeConstants.LOGGER.error("Unable to find \"value\" key in packmode JSON Condition: " + json);
            return false;
        }

        JsonElement packModes = value.getAsJsonObject().get("packModes");
        if (packModes == null) {
            PackModeConstants.LOGGER.warn("JsonObject: " + json + " has an empty packmode condition! This is the same as not including the condition at all.");
            return true;
        }

        if (packModes.isJsonPrimitive()) {
            return PackModeAPI.getInstance().includesPackMode(List.of(packModes.getAsString().toLowerCase(Locale.ROOT)));
        }
        else if (packModes.isJsonArray()) {
            List<String> packModeList = new ArrayList<>();
            JsonArray jsonArray = packModes.getAsJsonArray();
            for (int i = 0; i < jsonArray.size(); ++i) {
                packModeList.add(jsonArray.get(i).getAsString());
            }
            return PackModeAPI.getInstance().includesPackMode(packModeList);
        }
        return true;
    }

    //Call on forge
    public boolean test() {
        return PackModeAPI.getInstance().includesPackMode(validPackModes);
    }

    public List<String> getValidPackModes() {
        return this.validPackModes;
    }
}
