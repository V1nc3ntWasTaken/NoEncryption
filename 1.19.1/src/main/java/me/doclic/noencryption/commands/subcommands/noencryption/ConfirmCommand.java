package me.doclic.noencryption.commands.subcommands.noencryption;

import me.doclic.noencryption.NoEncryption;
import me.doclic.noencryption.VersionDownloader;
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
import java.util.Scanner;

public class ConfirmCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (NoEncryption.getPlugin().isRequestValid() && commandSender.hasPermission("noencryption.command.download")) {

            Bukkit.getScheduler().runTaskAsynchronously(NoEncryption.getPlugin(), () -> {

                String version;
                if (NoEncryption.getPlugin().getRequestType().equalsIgnoreCase("latest")) {
                    try {
                        version = new Scanner(new URL(NoEncryption.getPlugin().getConfig().getString("update-config.latest-check-url", "https://raw.githubusercontent.com/V1nc3ntWasTaken/NoEncryption/main/latest")).openStream(), StandardCharsets.UTF_8).useDelimiter("\\A").next();
                    } catch (IOException ex) {
                        if (commandSender instanceof Player plr) {
                            new Messaging().sendPlayerPluginMessage(plr, ChatColor.RED + "Failed to fetch the latest version from " + NoEncryption.getPlugin().getConfig().getString("update-config.latest-check-url", "https://raw.githubusercontent.com/V1nc3ntWasTaken/NoEncryption/main/latest" + ". Check the console for more details."), true, true);
                        }

                        new Messaging().sendConsolePluginInfoMessage(Bukkit.getConsoleSender(), ChatColor.RED + "Failed to fetch the latest version from " + NoEncryption.getPlugin().getConfig().getString("update-config.latest-check-url", "https://raw.githubusercontent.com/V1nc3ntWasTaken/NoEncryption/main/latest" + "."), true, true);

                        ex.printStackTrace();
                        return;
                    }
                } else {
                    version = NoEncryption.getPlugin().getDescription().getVersion();
                }

                String file = NoEncryption.getPlugin().getDescription().getName() + "-v" + version + "--" + Compatibility.getFormattedBukkitVersion() + "_only.jar";
                String downloadUrl = NoEncryption.getPlugin().getConfig().getString("update-config.file-download-url", "https://github.com/V1nc3ntWasTaken/NoEncryption/releases/download/%VERSION%/%FILE%");

                String url = downloadUrl
                        .replace("%VERSION%", version)
                        .replace("%FILE%", file);

                if (commandSender instanceof Player plr) {
                    new Messaging().sendPlayerPluginMessage(plr, ChatColor.YELLOW + "Downloading the compatible version from " + url + "...", true, true);
                    new Messaging().sendPlayerPluginMessage(plr, ChatColor.YELLOW + "Do not restart the server until the download success message is shown to prevent data loss", true, true);
                }

                new Messaging().sendConsolePluginInfoMessage(Bukkit.getConsoleSender(), ChatColor.YELLOW + "Downloading the compatible version from " + url + "...", true, true);
                new Messaging().sendConsolePluginInfoMessage(Bukkit.getConsoleSender(), ChatColor.YELLOW + "Do not restart the server until the download success message is shown to prevent data loss", true, true);

                VersionDownloader.downloadVersion(NoEncryption.getPlugin().getRequestType().equalsIgnoreCase("latest"), commandSender);

            });

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
