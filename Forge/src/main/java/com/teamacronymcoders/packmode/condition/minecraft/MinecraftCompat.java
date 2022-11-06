package com.teamacronymcoders.packmode.condition.minecraft;

import com.teamacronymcoders.packmode.PackModeConstants;
import com.teamacronymcoders.packmode.compat.Compat;
import com.teamacronymcoders.packmode.condition.minecraft.condition.PackModeConditionSerializer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = PackModeConstants.MODID)
public class MinecraftCompat extends Compat {

    @Override
    public void setup() {
        //Setup is called later than we'd like, so EBS it is
    }

    @SubscribeEvent
    public static void onRegisterSerializersEvent(RegistryEvent.Register<RecipeSerializer<?>> event) {
        CraftingHelper.register(new PackModeConditionSerializer());
    }
}
