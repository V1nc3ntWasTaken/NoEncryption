package me.doclic.noencryption;

import me.doclic.noencryption.compatibility.Compatibility;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

public class VersionDownloader {

    protected static NoEncryption plugin;

    protected static boolean deleteOnShutdown;

    public static void initialize(NoEncryption plugin) {

        VersionDownloader.plugin = plugin;

    }

    public static void downloadVersion() {

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {

            try {

                File saveLocation = new File("plugins/" + plugin.getDescription().getName() + "-v" + plugin.getDescription().getVersion() + "--" + Compatibility.getFormattedBukkitVersion() + "_only.jar");
                String url = "https://github.com/V1nc3ntWasTaken/NoEncryption/releases/download/" + plugin.getDescription().getVersion() + "/" + plugin.getDescription().getName() + "-v" + plugin.getDescription().getVersion() + "--" + Compatibility.getFormattedBukkitVersion() + "_only.jar";

                ReadableByteChannel byteChannel = Channels.newChannel(new URL(url).openStream());
                FileOutputStream outStream = new FileOutputStream(saveLocation);
                FileChannel channel = outStream.getChannel();

                channel.transferFrom(byteChannel, 0, Long.MAX_VALUE);

                doneDownloading();

            } catch (IOException e) {

                plugin.getLogger().severe("Failed to reach URL for download.");
                e.printStackTrace();

            }

        });

    }

    private static void doneDownloading() {
        setDeleteOnShutdown();

        plugin.getLogger().info("plugins/" + plugin.getDescription().getName() + "-v" + plugin.getDescription().getVersion() + "--" + Compatibility.getFormattedBukkitVersion() + "_only.jar" + " successfully downloaded");
        plugin.getLogger().info(NoEncryption.getJARFile().getName() + " (current version) will be deleted automatically upon restart");
        plugin.getLogger().info("It is now safe to restart your server");

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

            plugin.getLogger().severe("Unable to delete " + NoEncryption.getJARFile().getName() + ". Manual deletion is required");
            e.printStackTrace();

        }

    }

}
