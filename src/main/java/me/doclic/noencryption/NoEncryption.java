package me.doclic.noencryption;

import me.doclic.noencryption.commands.NoEncryptionCommand;
import me.doclic.noencryption.compatibility.Compatibility;
import me.doclic.noencryption.tabcomplete.NoEncryptionTab;
import me.doclic.noencryption.utils.Messaging;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Instant;

public final class NoEncryption extends JavaPlugin {
    static NoEncryption plugin;
    static boolean ready;
    private String downloadType;
    private Instant requestTime;

    @Override
    public void onEnable() {
        plugin = this;

        saveDefaultConfig();

        PlayerListener.startup();

        Compatibility.initialize(plugin);
        VersionDownloader.initialize(plugin);

        getCommand("noencryption").setExecutor(new NoEncryptionCommand());
        getCommand("noencryption").setTabCompleter(new NoEncryptionTab());

        if (Compatibility.checkCompatibility()) {

            new Messaging().sendConsolePluginInfoMessage(Bukkit.getConsoleSender(), "Compatibility successful!", true, true);

            new Messaging().sendConsolePluginInfoMessage(Bukkit.getConsoleSender(), "If you used /reload to update NoEncryption, your players need to disconnect and join back.", true, true);

            ready = true;

        } else {

            new Messaging().sendConsolePluginErrorMessage(Bukkit.getConsoleSender(), "Failed to setup compatibility!", true, true);
            new Messaging().sendConsolePluginErrorMessage(Bukkit.getConsoleSender(), "Your server version (" + Compatibility.getBukkitVersion() + ") is not compatible with this plugin!", true, true);

            ready = false;

            new Messaging().sendConsolePluginInfoMessage(Bukkit.getConsoleSender(), "To download the correct version, use /noencryption download.", true, true);

        }

        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
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

    public static boolean isReady() {
        return ready;
    }

    @Override
    public void onDisable() {
        if (NoEncryption.isReady()) {
            PlayerListener.shutdown();
        }

        VersionDownloader.shutdown();
    }

    public void createDownloadTimer(String downloadType, Instant requestTime) {
        this.downloadType = downloadType;
        this.requestTime = requestTime;
    }

    public boolean isRequestValid() {
        if (requestTime == null || downloadType == null) {
            return false;
        } else {
            return requestTime.getEpochSecond() - Instant.now().getEpochSecond() > -30;
        }
    }

    public String getRequestType() {
        return this.downloadType;
    }
}
