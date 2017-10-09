package io.sommers.packmode;

import com.google.common.collect.Lists;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.preprocessor.PreprocessorActionBase;
import crafttweaker.runtime.ScriptFile;
import io.sommers.packmode.api.PackModeAPI;

import java.util.Arrays;
import java.util.List;

public class PackModePreprocessor extends PreprocessorActionBase {
    private static final String PREPROCESSOR_NAME = "packmode";
    private List<String> packModes = Lists.newArrayList();


    public PackModePreprocessor(String fileName, String preprocessorLine, int lineIndex) {
        super(fileName, preprocessorLine, lineIndex);
        preprocessorLine = preprocessorLine.replace("#packmode", "");
        packModes.addAll(Lists.newArrayList(preprocessorLine.trim().split(" ")));
    }

    @Override
    public void executeActionOnFind(ScriptFile scriptFile) {
        if (!packModes.contains(PackModeAPI.getInstance().getCurrentPackMode())){
            CraftTweakerAPI.logInfo("Ignoring script " + scriptFile + " as current pack mode " + PackModeAPI.getInstance().getCurrentPackMode() +
                " is not in the scripts pack modes " + Arrays.toString(packModes.toArray()));

            scriptFile.setParsingBlocked(true);
            scriptFile.setCompileBlocked(true);
            scriptFile.setExecutionBlocked(true);
        }
    }

    @Override
    public String getPreprocessorName() {
        return PREPROCESSOR_NAME;
    }
}
