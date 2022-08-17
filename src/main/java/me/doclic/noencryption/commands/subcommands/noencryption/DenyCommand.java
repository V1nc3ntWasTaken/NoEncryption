package me.doclic.noencryption.commands.subcommands.noencryption;

import me.doclic.noencryption.NoEncryption;
import me.doclic.noencryption.utils.Messaging;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DenyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (NoEncryption.getPlugin().isRequestValid() && commandSender.hasPermission("noencryption.command.download")) {

            if (commandSender instanceof ConsoleCommandSender ccs) {
                new Messaging().sendConsolePluginInfoMessage(ccs, ChatColor.RED + "Download canceled.", true, true);
            } else if (commandSender instanceof Player plr) {
                new Messaging().sendPlayerPluginMessage(plr, ChatColor.RED + "Download canceled.", true, true);
            }

            NoEncryption.getPlugin().createDownloadTimer(null, null);

        } else {
            if (commandSender instanceof ConsoleCommandSender ccs) {
                new Messaging().sendConsolePluginInfoMessage(ccs, ChatColor.RED + "Invalid arguments. Usage: /" + s + " [download/reload]", true, true);
            } else if (commandSender instanceof Player plr) {
                new Messaging().sendPlayerPluginMessage(plr, ChatColor.RED + "Invalid arguments. Usage: /" + s + " [download/reload]", true, true);
            }
        }

        return true;
    }
}
