package me.doclic.noencryption;

import me.doclic.noencryption.compatibility.Compatibility;
import me.doclic.noencryption.utils.Messaging;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class VersionDownloader {

    protected static NoEncryption plugin;

    protected static boolean deleteOnShutdown;

    public static void initialize(NoEncryption plugin) {

        VersionDownloader.plugin = plugin;

    }

    @Deprecated
    public static void downloadVersion() {

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {

            try {

                File saveLocation = new File("plugins/" + plugin.getDescription().getName() + "-v" + plugin.getDescription().getVersion() + "--" + Compatibility.getFormattedBukkitVersion() + "_only.jar");
                String url = "https://github.com/V1nc3ntWasTaken/NoEncryption/releases/download/" + plugin.getDescription().getVersion() + "/" + plugin.getDescription().getName() + "-v" + plugin.getDescription().getVersion() + "--" + Compatibility.getFormattedBukkitVersion() + "_only.jar";

                ReadableByteChannel byteChannel = Channels.newChannel(new URL(url).openStream());
                FileOutputStream outStream = new FileOutputStream(saveLocation);
                FileChannel channel = outStream.getChannel();

                channel.transferFrom(byteChannel, 0, Long.MAX_VALUE);

                doneDownloading(Bukkit.getConsoleSender());

            } catch (IOException e) {
                new Messaging().sendConsolePluginInfoMessage(Bukkit.getConsoleSender(), ChatColor.RED + "Failed to fetch the latest version from " + NoEncryption.getPlugin().getConfig().getString("update-config.latest-check-url", "https://raw.githubusercontent.com/V1nc3ntWasTaken/NoEncryption/main/latest" + "."), true, true);

                e.printStackTrace();
            }

        });

    }

    public static void downloadVersion(boolean latest, CommandSender sender) {

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {

            String url, version;

            if (latest) {
                try {
                    version = new Scanner(new URL(plugin.getConfig().getString("update-config.latest-check-url", "https://raw.githubusercontent.com/V1nc3ntWasTaken/NoEncryption/main/latest")).openStream(), StandardCharsets.UTF_8).useDelimiter("\\A").next();
                } catch (IOException ex) {
                    if (sender instanceof Player plr) {
                        new Messaging().sendPlayerPluginMessage(plr, ChatColor.RED + "Failed to fetch the latest version from " + NoEncryption.getPlugin().getConfig().getString("update-config.latest-check-url", "https://raw.githubusercontent.com/V1nc3ntWasTaken/NoEncryption/main/latest" + ". Check the console for more details."), true, true);
                    }

                    new Messaging().sendConsolePluginInfoMessage(Bukkit.getConsoleSender(), ChatColor.RED + "Failed to fetch the latest version from " + NoEncryption.getPlugin().getConfig().getString("update-config.latest-check-url", "https://raw.githubusercontent.com/V1nc3ntWasTaken/NoEncryption/main/latest" + "."), true, true);

                    ex.printStackTrace();
                    return;
                }
            } else {
                version = plugin.getDescription().getVersion();
            }

            String file = plugin.getDescription().getName() + "-v" + version + "--" + Compatibility.getFormattedBukkitVersion() + "_only.jar";
            String downloadUrl = plugin.getConfig().getString("update-config.file-download-url", "https://github.com/V1nc3ntWasTaken/NoEncryption/releases/download/%VERSION%/%FILE%");

            url = downloadUrl
                    .replace("%VERSION%", version)
                    .replace("%FILE%", file);

            ReadableByteChannel byteChannel;
            try {
                byteChannel = Channels.newChannel(new URL(url).openStream());
            } catch (IOException ex) {
                if (sender instanceof Player plr) {
                    new Messaging().sendPlayerPluginMessage(plr, ChatColor.RED + "Failed to fetch the download url for " + url + ". Check the console for more details.", true, true);
                }

                new Messaging().sendConsolePluginInfoMessage(Bukkit.getConsoleSender(), ChatColor.RED + "Failed to fetch the download url for " + url + ". Check the console for more details.", true, true);
                return;
            }

            FileOutputStream outStream;
            FileChannel channel;
            try {
                outStream = new FileOutputStream(plugin.getDataFolder().getPath() + "/../" + file);
                channel = outStream.getChannel();
                channel.transferFrom(byteChannel, 0, Long.MAX_VALUE);
            } catch (IOException ex) {
                if (sender instanceof Player plr) {
                    new Messaging().sendPlayerPluginMessage(plr, ChatColor.RED + "Failed to save " + file + ". Check the console for more details.", true, true);
                }

                new Messaging().sendConsolePluginInfoMessage(Bukkit.getConsoleSender(), ChatColor.RED + "Failed to save " + file + ". Check the console for more details.", true, true);
            }

            doneDownloading(sender);

        });

    }

    private static void doneDownloading(CommandSender sender) {
        setDeleteOnShutdown();

        if (sender instanceof Player plr) {
            new Messaging().sendPlayerPluginMessage(plr, ChatColor.GREEN + "plugins/" + plugin.getDescription().getName() + "-v" + plugin.getDescription().getVersion() + "--" + Compatibility.getFormattedBukkitVersion() + "_only.jar" + " successfully downloaded.", true, true);
            new Messaging().sendPlayerPluginMessage(plr, ChatColor.GREEN + NoEncryption.getJARFile().getName() + " (current version) will be deleted automatically upon restart.", true, true);
            new Messaging().sendPlayerPluginMessage(plr, ChatColor.GREEN + "It is now safe to restart your server.", true, true);
        }

        new Messaging().sendConsolePluginInfoMessage(Bukkit.getConsoleSender(), ChatColor.GREEN + "plugins/" + plugin.getDescription().getName() + "-v" + plugin.getDescription().getVersion() + "--" + Compatibility.getFormattedBukkitVersion() + "_only.jar" + " successfully downloaded.", true, true);
        new Messaging().sendConsolePluginInfoMessage(Bukkit.getConsoleSender(), ChatColor.GREEN + NoEncryption.getJARFile().getName() + " (current version) will be deleted automatically upon restart.", true, true);
        new Messaging().sendConsolePluginInfoMessage(Bukkit.getConsoleSender(), ChatColor.GREEN + "It is now safe to restart your server.", true, true);

    }

    private static void setDeleteOnShutdown() {

        deleteOnShutdown = true;

    }

    public static boolean getDeleteOnShutdown() {

        return deleteOnShutdown;

    }

    public static void shutdown() {

        try {

            if (getDeleteOnShutdown()) {

                if (NoEncryption.getJARFile().exists() && !NoEncryption.getJARFile().delete()) {
                    throw new IOException();
                }

            }

        } catch (IOException e) {

            new Messaging().sendConsolePluginErrorMessage(Bukkit.getConsoleSender(), ChatColor.RED + "Unable to delete " + NoEncryption.getJARFile().getName() + ". Manual deletion is required.", true, true);
            e.printStackTrace();

        }

    }

}