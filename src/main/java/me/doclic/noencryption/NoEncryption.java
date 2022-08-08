package me.doclic.noencryption;

import me.doclic.noencryption.compatibility.Compatibility;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class NoEncryption extends JavaPlugin {

    static NoEncryption plugin;
    static boolean ready;

    @Override
    public void onEnable() {
        plugin = this;

        Compatibility.initialize(plugin);

        if (Compatibility.checkCompatibility()) {

            Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);

            getLogger().info("Compatibility successful!");

            getLogger().info("If you used /reload to update NoEncryption, your players need to");
            getLogger().info("disconnect and join back");

            ready = true;

        } else {

            getLogger().severe("Failed to setup NoEncryption's compatibility!");
            getLogger().severe("Your server version (" + Compatibility.getBukkitVersion() + ") is not compatible with this plugin!");

            ready = false;
        }

    }

    public static NoEncryption getPlugin() {

        return plugin;

    }

    public static boolean isReady() {

        return ready;

    }
}
