package com.teamacronymcoders.packmode;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.teamacronymcoders.packmode.api.PackModeAPI;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class PackModeCommand {
    private static final SimpleCommandExceptionType INVALID_PACK_MODE = new SimpleCommandExceptionType(
            new TranslationTextComponent("packmode.command.invalid"));


    public static LiteralArgumentBuilder<CommandSource> create() {
        return LiteralArgumentBuilder.<CommandSource>literal("packmode")
                .then(Commands.literal("set")
                        .then(Commands.argument("packmode", StringArgumentType.word())
                                .suggests((context, suggestionsBuilder) ->
                                        ISuggestionProvider.suggest(
                                                PackModeAPI.getInstance()
                                                        .getValidPackModes()
                                                        .stream(),
                                                suggestionsBuilder
                                        )
                                )
                                .executes(context -> {
                                    String newPackMode = context.getArgument("packmode", String.class);
                                    if (PackModeAPI.getInstance().isValidPackMode(newPackMode)) {
                                        PackModeAPI.getInstance().setPackMode(newPackMode);
                                        sendTranslatedFeedback(context, "changed", newPackMode);
                                        context.getSource().getServer().reload();
                                        return 1;
                                    } else {
                                        throw INVALID_PACK_MODE.create();
                                    }

                                })
                        )
                )
                .then(Commands.literal("current")
                    .executes(context -> {
                        sendTranslatedFeedback(context, "current", PackModeAPI.getInstance().getPackMode());
                        return 1;
                    })
                )
                .then(Commands.literal("list")
                    .executes(context -> {
                        sendTranslatedFeedback(context, "list", null);
                        for(String packMode: PackModeAPI.getInstance().getValidPackModes()) {
                            sendFeedback(context, new StringTextComponent("  " + packMode));
                        }
                        return 1;
                    })
                );
    }

    private static void sendTranslatedFeedback(CommandContext<CommandSource> context, String name, String data) {
        StringTextComponent component = data == null ? null : new StringTextComponent(data);
        sendFeedback(context, new TranslationTextComponent("packmode.command." + name, component));
    }

    private static void sendFeedback(CommandContext<CommandSource> context, TextComponent component) {
        context.getSource().sendFeedback(component, false);
    }
}
