package me.doclic.noencryption.compatibility;

import me.doclic.noencryption.NoEncryption;
import org.bukkit.Bukkit;

public class Compatibility {

    public static final boolean SERVER_COMPATIBLE;
    public static String MINECRAFT_VERSION, SERVER_VERSION;

    public static final String COMPATIBLE_MINECRAFT_VERSION;

    static {

        COMPATIBLE_MINECRAFT_VERSION = "1.19-R0.1-SNAPSHOT";

        try {

            MINECRAFT_VERSION = Bukkit.getBukkitVersion(); // Gets the server version displayable to a user
            SERVER_VERSION = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3]; // Gets the server version


        } catch (ArrayIndexOutOfBoundsException exception) {
            MINECRAFT_VERSION = null;
            SERVER_VERSION = null;
        }

        NoEncryption.getPlugin().getLogger().info("Your server is running version " + MINECRAFT_VERSION);

        SERVER_COMPATIBLE = MINECRAFT_VERSION.equals(COMPATIBLE_MINECRAFT_VERSION);

    }

}
