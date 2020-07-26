package com.teamacronymcoders.packmode.compat.crafttweaker;

import com.blamejared.crafttweaker.api.ScriptLoadingOptions;
import com.blamejared.crafttweaker.api.annotations.Preprocessor;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.PreprocessorMatch;
import com.blamejared.crafttweaker.api.zencode.impl.FileAccessSingle;
import com.teamacronymcoders.packmode.api.PackModeAPI;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Preprocessor
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
    public boolean apply(@Nonnull FileAccessSingle fileAccessSingle, ScriptLoadingOptions scriptLoadingOptions,
                         @Nonnull List<PreprocessorMatch> list) {
        return list.stream()
                .map(PreprocessorMatch::getContent)
                .map(content -> content.split(" "))
                .flatMap(Arrays::stream)
                .map(content -> content.toLowerCase(Locale.US))
                .anyMatch(PackModeAPI.getInstance().getPackMode()::equalsIgnoreCase);
    }
}
