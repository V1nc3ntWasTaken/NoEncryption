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

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Scanner;

public class LatestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSender.hasPermission("noencryption.command.download")) {
            Bukkit.getScheduler().runTaskAsynchronously(NoEncryption.getPlugin(), () -> {

                if (commandSender instanceof ConsoleCommandSender ccs) {
                    new Messaging().sendConsolePluginInfoMessage(ccs, ChatColor.YELLOW + "Fetching the " + ChatColor.AQUA + "latest" + ChatColor.YELLOW + " version of " + ChatColor.AQUA + "V1nc3ntWasTaken/NoEncryption" + ChatColor.YELLOW + "...", true, true);
                } else if (commandSender instanceof Player plr) {
                    new Messaging().sendPlayerPluginMessage(plr, ChatColor.YELLOW + "Fetching the " + ChatColor.AQUA + "latest" + ChatColor.YELLOW + " version of " + ChatColor.AQUA + "V1nc3ntWasTaken/NoEncryption" + ChatColor.YELLOW + "...", true, true);
                }

                String version;
                try {
                    version = new Scanner(new URL(NoEncryption.getPlugin().getConfig().getString("update-config.latest-check-url", "https://raw.githubusercontent.com/V1nc3ntWasTaken/NoEncryption/main/latest")).openStream(), StandardCharsets.UTF_8).useDelimiter("\\A").next();
                } catch (IOException ex) {
                    if (commandSender instanceof Player plr) {
                        new Messaging().sendPlayerPluginMessage(plr, ChatColor.RED + "Failed to fetch the latest version from " + NoEncryption.getPlugin().getConfig().getString("update-config.latest-check-url", "https://raw.githubusercontent.com/V1nc3ntWasTaken/NoEncryption/main/latest") + ". Check the console for more details.", true, true);
                    }

                    new Messaging().sendConsolePluginInfoMessage(Bukkit.getConsoleSender(), ChatColor.RED + "Failed to fetch the latest version from " + NoEncryption.getPlugin().getConfig().getString("update-config.latest-check-url", "https://raw.githubusercontent.com/V1nc3ntWasTaken/NoEncryption/main/latest") + ".", true, true);

                    ex.printStackTrace();
                    return;
                }
                String file = NoEncryption.getPlugin().getDescription().getName() + "-v" + version + "--" + Compatibility.getFormattedBukkitVersion() + "_only.jar";
                String downloadUrl = NoEncryption.getPlugin().getConfig().getString("update-config.file-download-url", "https://github.com/V1nc3ntWasTaken/NoEncryption/releases/download/%VERSION%/%FILE%");

                String url = downloadUrl
                        .replace("%VERSION%", version)
                        .replace("%FILE%", file);

                if (commandSender instanceof ConsoleCommandSender ccs) {
                    new Messaging().sendConsolePluginInfoMessage(ccs, ChatColor.YELLOW + "This will download the latest " + ChatColor.AQUA + "V1nc3ntWasTaken/NoEncryption" + ChatColor.YELLOW + " version supported by your server version (" + ChatColor.AQUA + Compatibility.getBukkitVersion() + ChatColor.YELLOW + ") from " + ChatColor.AQUA + url + ChatColor.YELLOW + ".", true, true);
                    new Messaging().sendConsolePluginInfoMessage(ccs, ChatColor.AQUA + file + ChatColor.YELLOW + " will be downloaded to the plugins folder. To confirm/deny this action, use " + ChatColor.AQUA + "/" + s + " [confirm/deny]" + ChatColor.YELLOW + ". This will timeout in 30 seconds.", true, true);
                } else if (commandSender instanceof Player plr) {
                    new Messaging().sendPlayerPluginMessage(plr, ChatColor.YELLOW + "This will download the latest " + ChatColor.AQUA + "V1nc3ntWasTaken/NoEncryption" + ChatColor.YELLOW + " version supported by your server version (" + ChatColor.AQUA + Compatibility.getBukkitVersion() + ChatColor.YELLOW + ") from " + ChatColor.AQUA + url + ChatColor.YELLOW + ".", true, true);
                    new Messaging().sendPlayerPluginMessage(plr, ChatColor.AQUA + file + ChatColor.YELLOW + " will be downloaded to the plugins folder. To confirm/deny this action, use " + ChatColor.AQUA + "/" + s + " [confirm/deny]" + ChatColor.YELLOW + ". This will timeout in 30 seconds.", true, true);
                }

                NoEncryption.getPlugin().createDownloadTimer("latest", Instant.now());

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