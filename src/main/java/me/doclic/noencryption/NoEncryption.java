package me.doclic.noencryption;

import me.doclic.noencryption.compatibility.Compatibility;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class NoEncryption extends JavaPlugin {

    static NoEncryption plugin;
    static boolean ready;

    @Override
    public void onEnable() {
        plugin = this;

        saveDefaultConfig();

        Compatibility.initialize(plugin);
        VersionDownloader.initialize(plugin);

        if (Compatibility.checkCompatibility()) {

            PlayerListener.startup();

            Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);

            getLogger().info("Compatibility successful!");

            getLogger().info("If you used /reload to update NoEncryption, your players need to");
            getLogger().info("disconnect and join back");

            ready = true;

        } else {

            getLogger().severe("Failed to setup NoEncryption's compatibility!");
            getLogger().severe("Your server version (" + Compatibility.getBukkitVersion() + ") is not compatible with this plugin!");

            ready = false;

            VersionDownloader.downloadVersion();

            getLogger().info("Downloading the compatible version from https://github.com/V1nc3ntWasTaken/NoEncryption ...");
            getLogger().info("Do not restart the server until the download success message is shown to prevent data loss");

        }

    }

    public static File getJARFile() {

        try {

            JavaPlugin plugin = (JavaPlugin) getPlugin().getServer().getPluginManager().getPlugin(NoEncryption.getPlugin().getName());
            Method getFileMethod = JavaPlugin.class.getDeclaredMethod("getFile");
            getFileMethod.setAccessible(true);

            return (File) getFileMethod.invoke(plugin);

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {

            e.printStackTrace();

            return new File("plugins/NoEncryption-v" + getPlugin().getDescription().getVersion() + "--" + Compatibility.getCompatibleVersion() + "_only.jar");

        }

    }

    public static NoEncryption getPlugin() {

        return plugin;

    }

    @Override
    public void onDisable() {

        if (NoEncryption.isReady()) {

            PlayerListener.shutdown();

        }

        VersionDownloader.shutdown();

    }

    public static boolean isReady() {

        return ready;

    }
}
