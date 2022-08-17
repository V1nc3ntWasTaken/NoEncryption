package me.doclic.noencryption.commands.subcommands.noencryption.subcommands.download;

import me.doclic.noencryption.NoEncryption;
import me.doclic.noencryption.compatibility.Compatibility;
import me.doclic.noencryption.utils.Messaging;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;

public class CurrentCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSender.hasPermission("noencryption.command.download")) {
            Bukkit.getScheduler().runTaskAsynchronously(NoEncryption.getPlugin(), () -> {

                String version = NoEncryption.getPlugin().getDescription().getVersion();
                String file = NoEncryption.getPlugin().getDescription().getName() + "-v" + version + "--" + Compatibility.getFormattedBukkitVersion() + "_only.jar";
                String downloadUrl = NoEncryption.getPlugin().getConfig().getString("update-config.file-download-url", "https://github.com/V1nc3ntWasTaken/NoEncryption/releases/download/%VERSION%/%FILE%");

                String url = downloadUrl
                        .replace("%VERSION%", version)
                        .replace("%FILE%", file);

                if (commandSender instanceof ConsoleCommandSender ccs) {
                    new Messaging().sendConsolePluginInfoMessage(ccs, ChatColor.YELLOW + "This will download the current " + ChatColor.AQUA + "V1nc3ntWasTaken/NoEncryption" + ChatColor.YELLOW + " version supported by your server version (" + ChatColor.AQUA + Compatibility.getBukkitVersion() + ChatColor.YELLOW + ") from " + ChatColor.AQUA + url + ChatColor.YELLOW + ".", true, true);
                    new Messaging().sendConsolePluginInfoMessage(ccs, ChatColor.AQUA + file + ChatColor.YELLOW + " will be downloaded to the plugins folder. To confirm/deny this action, use " + ChatColor.AQUA + "/" + s + " [confirm/deny]" + ChatColor.YELLOW + ". This will timeout in 30 seconds.", true, true);
                } else if (commandSender instanceof Player plr) {
                    new Messaging().sendPlayerPluginMessage(plr, ChatColor.YELLOW + "This will download the current " + ChatColor.AQUA + "V1nc3ntWasTaken/NoEncryption" + ChatColor.YELLOW + " version supported by your server version (" + ChatColor.AQUA + Compatibility.getBukkitVersion() + ChatColor.YELLOW + ") from " + ChatColor.AQUA + url + ChatColor.YELLOW + ".", true, true);
                    new Messaging().sendPlayerPluginMessage(plr, ChatColor.AQUA + file + ChatColor.YELLOW + " will be downloaded to the plugins folder. To confirm/deny this action, use " + ChatColor.AQUA + "/" + s + " [confirm/deny]" + ChatColor.YELLOW + ". This will timeout in 30 seconds.", true, true);
                }

                NoEncryption.getPlugin().createDownloadTimer("current", Instant.now());

            });
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
