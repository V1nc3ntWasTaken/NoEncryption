package me.doclic.noencryption.compatibility;

import org.bukkit.Bukkit;
import me.doclic.noencryption.compatibility.v1_19_R1.*;

public class Compatibility {

    public static final CompatiblePlayer COMPATIBLE_PLAYER;
    public static final CompatiblePacketListener COMPATIBLE_PACKET_LISTENER;

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

        Bukkit.getLogger().info("Your server is running version " + MINECRAFT_VERSION);

        if (MINECRAFT_VERSION.equals(COMPATIBLE_MINECRAFT_VERSION)) {

            COMPATIBLE_PLAYER = new CompatiblePlayer_v1_19_R1();
            COMPATIBLE_PACKET_LISTENER = new CompatiblePacketListener_v1_19_R1();

            SERVER_COMPATIBLE = true;

        } else {

            COMPATIBLE_PLAYER = null;
            COMPATIBLE_PACKET_LISTENER = null;

            SERVER_COMPATIBLE = false;

        }

    }

}
