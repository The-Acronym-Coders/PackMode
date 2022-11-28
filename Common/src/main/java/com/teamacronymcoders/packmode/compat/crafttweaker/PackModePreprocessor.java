package com.teamacronymcoders.packmode.compat.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.annotation.Preprocessor;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IMutableScriptRunInfo;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptFile;
import com.teamacronymcoders.packmode.PackModeAPI;
import com.teamacronymcoders.packmode.PackModeConstants;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

@Preprocessor
@ZenRegister
public class PackModePreprocessor implements IPreprocessor {

    public static final PackModePreprocessor INSTANCE = new PackModePreprocessor();

    private static final Set<String> RELOADABLE_LOADERS = Set.of(CraftTweakerConstants.DEFAULT_LOADER_NAME, CraftTweakerConstants.TAGS_LOADER_NAME);

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
        if (!RELOADABLE_LOADERS.contains(runInfo.loader().name()) )
            PackModeConstants.LOGGER.warn("PackMode Preprocessor was reloaded in a loader that does NOT support reloading. This means you will not be able to change PackModes on the fly");
        return matches.stream().map(Match::content).anyMatch(PackModeAPI.getInstance().getPackMode()::equalsIgnoreCase);
    }
}
