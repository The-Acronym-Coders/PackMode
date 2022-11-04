package com.teamacronymcoders.packmode.condition.minecraft.condition;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.teamacronymcoders.packmode.PackModeConstants;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class PackModeConditionSerializer implements IConditionSerializer<PackModeCondition> {
    private static final ResourceLocation id = new ResourceLocation(PackModeConstants.MODID, "active");


    @Override
    public void write(JsonObject json, PackModeCondition value) {
        JsonArray packModes = new JsonArray();
        value.getValidPackModes().forEach(packModes::add);
        json.add("packModes", packModes);
    }

    @Override
    public PackModeCondition read(JsonObject json) {
        JsonElement packModes = json.get("packModes");
        PackModeCondition condition;
        if (packModes.isJsonPrimitive()) {
            condition = new PackModeCondition(Lists.newArrayList(packModes.getAsJsonPrimitive().getAsString()));
        } else if (packModes.isJsonArray()) {
            condition = new PackModeCondition(StreamSupport.stream(packModes.getAsJsonArray().spliterator(), false)
                    .map(packModeElement -> packModeElement.getAsJsonPrimitive().getAsString())
                    .collect(Collectors.toList())
            );
        } else {
            condition = new PackModeCondition(Lists.newArrayList());
        }
        return condition;
    }

    @Override
    public ResourceLocation getID() {
        return id;
    }
}
