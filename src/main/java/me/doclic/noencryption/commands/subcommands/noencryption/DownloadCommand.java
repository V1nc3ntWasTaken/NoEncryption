package me.doclic.noencryption.commands.subcommands.noencryption;

import me.doclic.noencryption.commands.subcommands.noencryption.subcommands.download.CurrentCommand;
import me.doclic.noencryption.commands.subcommands.noencryption.subcommands.download.LatestCommand;
import me.doclic.noencryption.utils.Messaging;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class DownloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        try {

            switch (strings[0].toLowerCase()) {
                case "current":
                    return new CurrentCommand().onCommand(commandSender, command, s, Arrays.copyOfRange(strings, 1, strings.length));
                case "latest":
                    return new LatestCommand().onCommand(commandSender, command, s, Arrays.copyOfRange(strings, 1, strings.length));
                default:
                    if (commandSender instanceof ConsoleCommandSender ccs) {
                        new Messaging().sendConsolePluginInfoMessage(ccs, ChatColor.RED + "Invalid arguments. Usage: /" + s + " download [current/latest]", true, true);
                    } else if (commandSender instanceof Player plr) {
                        new Messaging().sendPlayerPluginMessage(plr, ChatColor.RED + "Invalid arguments. Usage: /" + s + " download [current/latest]", true, true);
                    }
                    break;
            }

        } catch (ArrayIndexOutOfBoundsException ignored) {
            if (commandSender instanceof ConsoleCommandSender ccs) {
                new Messaging().sendConsolePluginInfoMessage(ccs, ChatColor.RED + "Invalid arguments. Usage: /" + s + " download [current/latest]", true, true);
            } else if (commandSender instanceof Player plr) {
                new Messaging().sendPlayerPluginMessage(plr, ChatColor.RED + "Invalid arguments. Usage: /" + s + " download [current/latest]", true, true);
            }
        }

        return true;
    }
}
