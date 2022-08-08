package me.doclic.noencryption.compatibility;

import me.doclic.noencryption.NoEncryption;
import org.bukkit.Bukkit;

public class Compatibility {

    protected static NoEncryption plugin;

    protected static String compatibleVersion;
    protected static String bukkitVersion;
    protected static String formattedBukkitVersion;
    protected static String minecraftVersion;

    protected static boolean compatible;

    public static void initialize(NoEncryption plugin) {
        Compatibility.plugin = plugin;

        // 1.19 is "1.19-R0.1-SNAPSHOT"
        Compatibility.compatibleVersion = "1.19-R0.1-SNAPSHOT";

        try {
            bukkitVersion = Bukkit.getBukkitVersion(); // Gets the server version displayable to a user
            formattedBukkitVersion = bukkitVersion.split("-")[0];
            minecraftVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3]; // Gets the server version
        } catch (ArrayIndexOutOfBoundsException exception) {
            // This should never happen
            bukkitVersion = null;
            formattedBukkitVersion = null;
            minecraftVersion = null;
        }

        plugin.getLogger().info("Your server is running version " + bukkitVersion);

        compatible = bukkitVersion.equals(compatibleVersion);
    }

    public static String getCompatibleVersion() {
        return compatibleVersion;
    }

    public static boolean checkCompatibility() {
        return compatible;
    }

    public static String getBukkitVersion() {
        return bukkitVersion;
    }

    public static String getFormattedBukkitVersion() {
        return formattedBukkitVersion;
    }
}
