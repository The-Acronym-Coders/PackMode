package com.teamacronymcoders;

import com.teamacronymcoders.packmode.PackModeAPIImpl;
import com.teamacronymcoders.packmode.PackModeCommand;
import com.teamacronymcoders.packmode.PackModeConstants;
import com.teamacronymcoders.packmode.api.PackModeAPI;
import com.teamacronymcoders.packmode.compat.CompatHandler;
import com.teamacronymcoders.packmode.condition.minecraft.MinecraftCompat;
import com.teamacronymcoders.packmode.condition.minecraft.condition.PackModeCondition;
import com.teamacronymcoders.packmode.platform.ForgeConfigHelper;
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
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

import java.util.List;
import java.util.function.Consumer;

@Mod(value = PackModeConstants.MODID)
public class PackMode {

    public PackMode() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::gatherDataEvent);
        MinecraftForge.EVENT_BUS.addListener(this::onServerStarting);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ForgeConfigHelper.makeConfig(new ForgeConfigSpec.Builder()));
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        PackModeAPI.setInstance(new PackModeAPIImpl());
        CompatHandler.registerCompat("minecraft", MinecraftCompat.class.getName());
        CompatHandler.tryActivate();
        CompatHandler.setup();
    }

    private void gatherDataEvent(final GatherDataEvent event) {

        if (event.includeServer() && event.includeDev()) {
            event.getGenerator().addProvider(new RecipeProvider(event.getGenerator()) {
                @Override
                protected void buildCraftingRecipes(Consumer<FinishedRecipe> p_176532_) {
                    ConditionalRecipe.builder()
                            .addCondition(new PackModeCondition(List.of("expert")))
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
