package com.teamacronymcoders.packmode.compat.crafttweaker;

import com.blamejared.crafttweaker.api.annotation.Preprocessor;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IMutableScriptRunInfo;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptFile;
import com.teamacronymcoders.packmode.api.PackModeAPI;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Preprocessor
@ZenRegister
public class PackModePreprocessor implements IPreprocessor {

    public static final PackModePreprocessor INSTANCE = new PackModePreprocessor();

    @Override
    public String name() {
        return "packmode";
    }

    @Nullable
    @Override
    public String defaultValue() {
        return null;
    }

    @Override
    public boolean apply(IScriptFile file, List<String> preprocessedContents, IMutableScriptRunInfo runInfo, List<Match> matches) {
        return matches.stream().map(Match::content).anyMatch(PackModeAPI.getInstance().getPackMode()::equalsIgnoreCase);
    }
}
