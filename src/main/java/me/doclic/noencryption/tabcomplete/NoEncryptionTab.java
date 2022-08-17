package me.doclic.noencryption.tabcomplete;

import me.doclic.noencryption.tabcomplete.subcommands.DownloadTab;
import me.doclic.noencryption.tabcomplete.subcommands.ReloadTab;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NoEncryptionTab implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        try {
            return switch (strings[0].toLowerCase()) {
                case "download" -> new DownloadTab().onTabComplete(commandSender, command, s, Arrays.copyOfRange(strings, 1, strings.length));
                case "reload" -> new ReloadTab().onTabComplete(commandSender, command, s, Arrays.copyOfRange(strings, 1, strings.length));
                default -> StringUtil.copyPartialMatches(strings[0], Arrays.asList("download", "reload"), new ArrayList<>());
            };
        } catch (ArrayIndexOutOfBoundsException ignored) {
            return StringUtil.copyPartialMatches(strings[0], Arrays.asList("download", "reload"), new ArrayList<>());
        }
    }
}
