package com.teamacronymcoders.packmode;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.teamacronymcoders.packmode.api.PackModeAPI;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;


public class PackModeCommand {
    private static final SimpleCommandExceptionType INVALID_PACK_MODE = new SimpleCommandExceptionType(
            Component.translatable("packmode.command.invalid"));
    public static final SuggestionProvider<CommandSourceStack> SUGGEST_PACKMODE = (commandSource, suggestionsBuilder) ->
        SharedSuggestionProvider.suggest(PackModeAPI.getInstance().getValidPackModes(), suggestionsBuilder);

    public static LiteralArgumentBuilder<CommandSourceStack> create() {
        return Commands.literal("packmode").requires(source -> source.hasPermission(2))
                .then(Commands.literal("set")
                        .then(Commands.argument("packmode", StringArgumentType.word())
                                .suggests(SUGGEST_PACKMODE)
                                .executes(context -> {
                                    String newPackMode = context.getArgument("packmode", String.class);
                                    if (PackModeAPI.getInstance().isValidPackMode(newPackMode)) {
                                        PackModeAPI.getInstance().setPackMode(newPackMode);
                                        sendTranslatedFeedback(context, "changed", newPackMode);
                                        MinecraftServer server = context.getSource().getServer();
                                        PackRepository packRepository = server.getPackRepository();
                                        packRepository.reload();
                                        server.reloadResources(packRepository.getSelectedPacks().stream().map(Pack::getId).toList());
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
                            sendFeedback(context, Component.literal("  " + packMode));
                        }
                        return 1;
                    })
                );
    }

    private static void sendTranslatedFeedback(CommandContext<CommandSourceStack> context, String name, String data) {
        sendFeedback(context, Component.translatable("packmode.command." + name, data));
    }

    private static void sendFeedback(CommandContext<CommandSourceStack> context, Component component) {
        context.getSource().sendSuccess(component, true);
    }
}
