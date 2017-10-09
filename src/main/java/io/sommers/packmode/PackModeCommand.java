package io.sommers.packmode;

import com.google.common.collect.Lists;
import io.sommers.packmode.api.PackModeAPI;
import joptsimple.internal.Strings;
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
        return "/packmode <name> Sets the Packmode. Current valid packmodes are " +
                Strings.join(PackModeAPI.getInstance().getPackModes(), " , ");
    }

    @Override
    @ParametersAreNonnullByDefault
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 1) {
            String newPackMode = args[0].trim();
            if (PackModeAPI.getInstance().isValidPackMode(newPackMode)) {
                PackModeAPI.getInstance().setNextRestartPackMode(newPackMode);
                sender.sendMessage(new TextComponentString("PackMode is now " + newPackMode + ". Please restart to " +
                        "enjoy the new PackMode."));
            } else {
                throw new CommandException("PackMode " + newPackMode + " is not in the list of valid PackModes.");
            }
        } else if (args.length == 0){
            sender.sendMessage(new TextComponentString("Current PackMode is " + PackModeAPI.getInstance().getCurrentPackMode() +
                ". PackMode next Restart will be " + PackModeAPI.getInstance().getNextRestartPackMode() + ". Valid " +
                " PackModes are " + Strings.join(PackModeAPI.getInstance().getPackModes(), " , ")));
        } else {
            throw new CommandException("Incorrect number of parameters requires 0 or 1, found " + args.length);
        }

    }
}
