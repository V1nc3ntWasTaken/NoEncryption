package me.doclic.noencryption;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import me.doclic.noencryption.compatibility.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    public static void startup() {

        if (NoEncryption.isReady()) {

            for (final Player player : Bukkit.getOnlinePlayers()) {

                final ChannelPipeline pipeline = new CompatiblePlayer().getChannel(player).pipeline();
                pipeline.addBefore("packet_handler", "no_encryption_interceptor", new ChannelDuplexHandler() {

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

        }

    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin (PlayerJoinEvent e) {

        if (NoEncryption.isReady() && e.getPlayer().isOnline()) {

            final Player player = e.getPlayer();
            final ChannelPipeline pipeline = new CompatiblePlayer().getChannel(player).pipeline();
            pipeline.addBefore("packet_handler", "no_encryption_interceptor", new ChannelDuplexHandler() {

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

    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit (PlayerQuitEvent e) {

        if (NoEncryption.isReady()) {

            final Player player = e.getPlayer();
            final Channel channel = new CompatiblePlayer().getChannel(player);
            channel.eventLoop().submit(() -> channel.pipeline().remove("no_encryption_interceptor"));

        }

    }

    public static void shutdown() {

        if (NoEncryption.isReady()) {

            for (final Player player : Bukkit.getOnlinePlayers()) {

                final Channel channel = new CompatiblePlayer().getChannel(player);
                channel.eventLoop().submit(() -> channel.pipeline().remove("no_encryption_interceptor"));

            }

        }

    }

}
