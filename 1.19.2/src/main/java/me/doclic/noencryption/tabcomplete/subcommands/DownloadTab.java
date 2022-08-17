package me.doclic.noencryption.tabcomplete.subcommands;

import me.doclic.noencryption.tabcomplete.subcommands.subcommands.download.CurrentTab;
import me.doclic.noencryption.tabcomplete.subcommands.subcommands.download.LatestTab;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DownloadTab implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        try {
            return switch (strings[0].toLowerCase()) {
                case "current" -> new CurrentTab().onTabComplete(commandSender, command, s, Arrays.copyOfRange(strings, 1, strings.length));
                case "latest" -> new LatestTab().onTabComplete(commandSender, command, s, Arrays.copyOfRange(strings, 1, strings.length));
                default -> StringUtil.copyPartialMatches(strings[0], Arrays.asList("current", "latest"), new ArrayList<>());
            };
        } catch (ArrayIndexOutOfBoundsException ignored) {
            return StringUtil.copyPartialMatches(strings[0], Arrays.asList("current", "latest"), new ArrayList<>());
        }
    }
}
