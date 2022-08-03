package me.doclic.noencryption.compatibility;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.network.chat.*;
import net.minecraft.network.protocol.game.ClientboundPlayerChatPacket;

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

        return packet;

    }

}
