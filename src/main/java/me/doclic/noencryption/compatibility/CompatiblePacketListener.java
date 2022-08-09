package me.doclic.noencryption.compatibility;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import me.doclic.noencryption.NoEncryption;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.network.chat.*;
import net.minecraft.network.protocol.game.ClientboundPlayerChatHeaderPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerChatPacket;
import net.minecraft.network.protocol.game.ClientboundServerDataPacket;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;

import java.util.Optional;
import java.util.UUID;

public class CompatiblePacketListener {

    public Object readPacket(ChannelHandlerContext channelHandlerContext, Object packet) { return packet; }

    public Object writePacket(ChannelHandlerContext channelHandlerContext, Object packet, ChannelPromise promise) {
        if (packet instanceof final ClientboundPlayerChatPacket clientboundPlayerChatPacket) {
            // Code partially by atenfyr https://github.com/atenfyr
            final Optional<Component> unsignedContent = clientboundPlayerChatPacket.message().unsignedContent();
            final ChatMessageContent signedContent = clientboundPlayerChatPacket.message().signedContent();
            final SignedMessageBody signedBody = clientboundPlayerChatPacket.message().signedBody();
            final ChatType.BoundNetwork chatType = clientboundPlayerChatPacket.chatType();

            // recreate a new packet
            return new ClientboundPlayerChatPacket(
                    new PlayerChatMessage(
                            new SignedMessageHeader(
                                    new MessageSignature(new byte[0]),
                                    new UUID(0,0)),
                            new MessageSignature(new byte[0]),
                            new SignedMessageBody(
                                    new ChatMessageContent(
                                            signedContent.plain(),
                                            signedContent.decorated()),
                                    signedBody.timeStamp(),
                                    0,
                                    signedBody.lastSeen()),
                            unsignedContent,
                            new FilterMask(0)
                    ),
                    chatType);
        }

        if (packet instanceof final ClientboundSystemChatPacket clientboundSystemChatPacket) {
            if (clientboundSystemChatPacket.content() == null) {
                return clientboundSystemChatPacket;
            } else {
                return new ClientboundSystemChatPacket(
                        ComponentSerializer.parse(clientboundSystemChatPacket.content()),
                        clientboundSystemChatPacket.overlay());
            }
        }

        if (packet instanceof final ClientboundPlayerChatHeaderPacket clientboundPlayerChatHeaderPacket) {
            return new ClientboundPlayerChatHeaderPacket(
                    new SignedMessageHeader(
                            new MessageSignature(new byte[0]),
                            new UUID(0,0)),
                    new MessageSignature(new byte[0]),
                    clientboundPlayerChatHeaderPacket.bodyDigest()
            );
        }

        if (packet instanceof final ClientboundServerDataPacket clientboundServerDataPacket) {
            if (NoEncryption.getPlugin().getConfig().getBoolean("disable-unsecure-banner", true)) {
                // recreate a new packet
                new ClientboundServerDataPacket(
                        clientboundServerDataPacket.getMotd().orElse(null),
                        clientboundServerDataPacket.getIconBase64().orElse(null),
                        clientboundServerDataPacket.previewsChat(),
                        true
                );
            }
        }

        return packet;

    }

}
