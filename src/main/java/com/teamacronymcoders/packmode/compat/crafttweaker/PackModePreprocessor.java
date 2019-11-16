package com.teamacronymcoders.packmode.compat.crafttweaker;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.annotations.PreProcessor;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.PreprocessorMatch;
import com.blamejared.crafttweaker.api.zencode.impl.FileAccessSingle;
import com.teamacronymcoders.packmode.PackMode;
import com.teamacronymcoders.packmode.api.PackModeAPI;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@PreProcessor
public class PackModePreprocessor implements IPreprocessor {
    @Override
    public String getName() {
        return "packmode";
    }

    @Nullable
    @Override
    public String getDefaultValue() {
        return null;
    }

    @Override
    public boolean apply(@Nonnull FileAccessSingle fileAccessSingle, @Nonnull List<PreprocessorMatch> list) {
        return list.stream()
                .map(PreprocessorMatch::getContent)
                .map(content -> content.split(" "))
                .flatMap(Arrays::stream)
                .map(content -> content.toLowerCase(Locale.US))
                .anyMatch(PackModeAPI.getInstance().getPackMode()::equalsIgnoreCase);
    }
}
