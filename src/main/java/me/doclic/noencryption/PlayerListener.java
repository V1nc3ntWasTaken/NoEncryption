package me.doclic.noencryption;

import io.netty.channel.*;
import me.doclic.noencryption.compatibility.CompatiblePacketListener;
import me.doclic.noencryption.compatibility.CompatiblePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin (PlayerJoinEvent e) {

        final Player player = e.getPlayer();
        final ChannelPipeline pipeline = new CompatiblePlayer().getChannel(player).pipeline();
        pipeline.addBefore("packet_handler", "no_encryption", new ChannelDuplexHandler() {

            @Override
            public void channelRead(ChannelHandlerContext channelHandlerContext, Object packet) throws Exception {

                Object newPacket = new CompatiblePacketListener().readPacket(channelHandlerContext, packet);
                super.channelRead(channelHandlerContext, newPacket);

            }

            @Override
            public void write(ChannelHandlerContext channelHandlerContext, Object packet, ChannelPromise promise) throws Exception {

                Object newPacket = new CompatiblePacketListener().writePacket(channelHandlerContext, packet, promise);
                super.write(channelHandlerContext, newPacket, promise);

            }

        });

    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit (PlayerQuitEvent e) {

        final Player player = e.getPlayer();
        final Channel channel = new CompatiblePlayer().getChannel(player);
        channel.eventLoop().submit(() -> channel.pipeline().remove("no_encryption"));

    }

}
