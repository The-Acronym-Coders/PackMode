package io.sommers.packmode;

import com.google.common.collect.Lists;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;

public class PackModeCommand extends CommandBase {
    @Override
    @Nonnull
    public String getName() {
        return "packmode";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "Changed the Packmode, current accepted packmodes are " + Arrays.toString(PMConfig.acceptedModes);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 1) {
            String newPackMode = args[0].trim();
            if (Lists.newArrayList(PMConfig.acceptedModes).contains(newPackMode)) {
                PMConfig.packMode = newPackMode;
                sender.sendMessage(new TextComponentString("PackMode is now " + newPackMode + ". Please restart to" +
                    "enjoy the new PackMode."));
            } else {
                throw new CommandException("PackMode " + newPackMode + " is not in the list of accepted PackModes.");
            }
        } else {
            throw new CommandException("Incorrect number of parameters requires 1, found " + args.length);
        }

    }
}
