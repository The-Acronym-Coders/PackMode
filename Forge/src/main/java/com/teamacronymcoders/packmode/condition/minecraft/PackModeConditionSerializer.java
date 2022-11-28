package com.teamacronymcoders.packmode.condition.minecraft;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.teamacronymcoders.packmode.PackModeConstants;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class PackModeConditionSerializer implements IConditionSerializer<ForgePackModeCondition> {
    private static final ResourceLocation id = new ResourceLocation(PackModeConstants.MODID, "active");


    @Override
    public void write(JsonObject json, ForgePackModeCondition value) {
        JsonArray packModes = new JsonArray();
        value.getValidPackModes().forEach(packModes::add);
        json.add("packModes", packModes);
    }

    @Override
    public ForgePackModeCondition read(JsonObject json) {
        JsonElement packModes = json.get("packModes");
        ForgePackModeCondition condition;
        if (packModes == null) {
            PackModeConstants.LOGGER.warn("JsonObject: " + json + " has an empty packmode condition! This is the same as not including the condition at all.");
            return new ForgePackModeCondition(Lists.newArrayList());
        }
        if (packModes.isJsonPrimitive()) {
            condition = new ForgePackModeCondition(Lists.newArrayList(packModes.getAsJsonPrimitive().getAsString()));
        } else if (packModes.isJsonArray()) {
            condition = new ForgePackModeCondition(StreamSupport.stream(packModes.getAsJsonArray().spliterator(), false)
                    .map(packModeElement -> packModeElement.getAsJsonPrimitive().getAsString())
                    .collect(Collectors.toList())
            );
        } else {
            condition = new ForgePackModeCondition(Lists.newArrayList());
        }
        return condition;
    }

    @Override
    public ResourceLocation getID() {
        return id;
    }
}
