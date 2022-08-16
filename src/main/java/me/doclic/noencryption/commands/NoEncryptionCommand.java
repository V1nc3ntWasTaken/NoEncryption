package me.doclic.noencryption.commands;

import me.doclic.noencryption.commands.subcommands.noencryption.ConfirmCommand;
import me.doclic.noencryption.commands.subcommands.noencryption.DenyCommand;
import me.doclic.noencryption.commands.subcommands.noencryption.DownloadCommand;
import me.doclic.noencryption.commands.subcommands.noencryption.ReloadCommand;
import me.doclic.noencryption.utils.Messaging;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class NoEncryptionCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSender.hasPermission("noencryption.command.noencryption")) {
            try {

                switch (strings[0].toLowerCase()) {
                    case "download":
                        return new DownloadCommand().onCommand(commandSender, command, s, Arrays.copyOfRange(strings, 1, strings.length));
                    case "reload":
                        return new ReloadCommand().onCommand(commandSender, command, s, Arrays.copyOfRange(strings, 1, strings.length));
                    case "confirm":
                        return new ConfirmCommand().onCommand(commandSender, command, s, Arrays.copyOfRange(strings, 1, strings.length));
                    case "deny":
                        return new DenyCommand().onCommand(commandSender, command, s, Arrays.copyOfRange(strings, 1, strings.length));
                    default:
                        if (commandSender instanceof ConsoleCommandSender ccs) {
                            new Messaging().sendConsolePluginInfoMessage(ccs, ChatColor.RED + "Invalid arguments. Usage: /" + s + " [download/reload]", true, true);
                        } else if (commandSender instanceof Player plr) {
                            new Messaging().sendPlayerPluginMessage(plr, ChatColor.RED + "Invalid arguments. Usage: /" + s + " [download/reload]", true, true);
                        }
                        break;
                }

            } catch (ArrayIndexOutOfBoundsException ignored) {
                if (commandSender instanceof ConsoleCommandSender ccs) {
                    new Messaging().sendConsolePluginInfoMessage(ccs, ChatColor.RED + "Invalid arguments. Usage: /" + s + " [download/reload]", true, true);
                } else if (commandSender instanceof Player plr) {
                    new Messaging().sendPlayerPluginMessage(plr, ChatColor.RED + "Invalid arguments. Usage: /" + s + " [download/reload]", true, true);
                }
            }
        } else {
            if (commandSender instanceof ConsoleCommandSender ccs) {
                new Messaging().sendConsolePluginErrorMessage(ccs, ChatColor.RED + "You do not have permission to use this command!", true, true);
            } else if (commandSender instanceof Player plr) {
                new Messaging().sendPlayerPluginMessage(plr, ChatColor.RED + "You do not have permission to use this command!", true, true);
            }
        }

        return true;
    }
}
