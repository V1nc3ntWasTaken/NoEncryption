package me.doclic.noencryption.compatibility;

import io.netty.channel.Channel;
import me.doclic.noencryption.NoEncryption;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class CompatiblePlayer {

    public Channel getChannel(Player player) {

        if (NoEncryption.isReady()) {

            return ((CraftPlayer) player).getHandle().connection.connection.channel;

        } else {

            return null;

        }

    }

}
