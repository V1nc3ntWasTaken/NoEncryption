package me.doclic.noencryption.utils;

import me.doclic.noencryption.NoEncryption;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Messaging {
    public void sendPlayerPluginMessage(Player player, String msg, boolean prefixColored, boolean msgColored) {
        player.sendMessage((prefixColored ? ChatColor.translateAlternateColorCodes('&', NoEncryption.getPlugin().getConfig().getString("player-prefix", "&f[&aNo&bEncryption&f] ")) : NoEncryption.getPlugin().getConfig().getString("player-prefix", "&f[&aNo&bEncryption&f] ")) + ChatColor.RESET + (msgColored ? ChatColor.translateAlternateColorCodes('&', msg) : msg));
    }

    public void sendConsolePluginInfoMessage(ConsoleCommandSender sender, String msg, boolean prefixColored, boolean msgColored) {
        sender.sendMessage((prefixColored ? ChatColor.translateAlternateColorCodes('&', NoEncryption.getPlugin().getConfig().getString("player-prefix", "&f[&aNo&bEncryption&f] ")) : NoEncryption.getPlugin().getConfig().getString("player-prefix", "&f[&aNo&bEncryption&f] ")) + ChatColor.RESET + (msgColored ? ChatColor.translateAlternateColorCodes('&', msg) : msg));
    }

    public void sendConsolePluginErrorMessage(ConsoleCommandSender sender, String msg, boolean prefixColored, boolean msgColored) {
        sender.sendMessage((prefixColored ? ChatColor.translateAlternateColorCodes('&', NoEncryption.getPlugin().getConfig().getString("player-prefix", "&f[&aNo&bEncryption&f] ")) : NoEncryption.getPlugin().getConfig().getString("player-prefix", "&f[&aNo&bEncryption&f] ")) + ChatColor.RED + (msgColored ? ChatColor.translateAlternateColorCodes('&', msg) : msg));
    }
}