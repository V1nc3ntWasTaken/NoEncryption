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

public class ReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSender.hasPermission("noencryption.command.reload")) {
            NoEncryption.getPlugin().reloadConfig();

            if (commandSender instanceof ConsoleCommandSender ccs) {
                new Messaging().sendConsolePluginInfoMessage(ccs, ChatColor.GREEN + "Configuration reloaded!", true, true);
            } else if (commandSender instanceof Player plr) {
                new Messaging().sendPlayerPluginMessage(plr, ChatColor.GREEN + "Configuration reloaded!", true, true);
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
