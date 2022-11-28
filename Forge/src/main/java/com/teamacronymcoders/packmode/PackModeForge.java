package com.teamacronymcoders.packmode;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.teamacronymcoders.packmode.compat.CompatHandler;
import com.teamacronymcoders.packmode.compat.minecraft.MinecraftCompat;
import com.teamacronymcoders.packmode.condition.minecraft.ForgePackModeCondition;
import com.teamacronymcoders.packmode.platform.PackModeForgeConfigHelper;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

import java.util.List;
import java.util.function.Consumer;

@Mod(value = PackModeConstants.MODID)
public class PackModeForge {

    public PackModeForge() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::gatherDataEvent);
        MinecraftForge.EVENT_BUS.addListener(this::onServerStarting);
        ForgeConfigSpec spec = PackModeForgeConfigHelper.makeConfig(new ForgeConfigSpec.Builder());


        //Load the Config Earlier through NightConfig to resolve it as earlier as possible
        final CommentedFileConfig file = CommentedFileConfig.builder(FMLPaths.CONFIGDIR.get().resolve("packmode-common.toml")).sync().autosave().writingMode(WritingMode.REPLACE).build();
        file.load();
        spec.setConfig(file);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, spec);

        //Setup PackMode
        PackModeAPI.setInstance(new PackModeAPIImpl());
        CompatHandler.registerCompat("minecraft", MinecraftCompat.class);
        CompatHandler.tryActivate();
        CompatHandler.setup();
    }

    private void gatherDataEvent(final GatherDataEvent event) {

        if (event.includeServer() && event.includeDev()) {
            event.getGenerator().addProvider(new RecipeProvider(event.getGenerator()) {
                @Override
                protected void buildCraftingRecipes(Consumer<FinishedRecipe> p_176532_) {
                    ConditionalRecipe.builder()
                            .addCondition(new ForgePackModeCondition(List.of("expert")))
                            .addRecipe(
                                    ShapedRecipeBuilder.shaped(Items.TNT)
                                            .pattern("LLL")
                                            .pattern("LBL")
                                            .pattern("LGL")
                                            .define('L', ItemTags.LOGS)
                                            .define('B', Items.BREAD)
                                            .define('G', Items.GLOWSTONE_DUST)
                                            .unlockedBy("has_stone", has(Items.BREAD))
                                            ::save).build(p_176532_, new ResourceLocation("packmode", "test_recipe"));
                }
            });

        }
    }

    private void onServerStarting(RegisterCommandsEvent event) {
        event.getDispatcher().register(PackModeCommand.create());
    }
}
