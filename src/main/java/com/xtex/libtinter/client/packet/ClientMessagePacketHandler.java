package com.xtex.libtinter.client.packet;

import com.xtex.libtinter.client.TinterClientSession;
import com.xtex.libtinter.TinterPacket;
import com.xtex.libtinter.util.IllegalPacketException;

/**
 * Client packet handler for <code>message</code> packet
 */
public class ClientMessagePacketHandler implements IClientPacketHandler {

    @Override
    public void accept(TinterClientSession session, TinterPacket packet) {
        if (packet.subtype == null)
            throw new IllegalPacketException("'message' packet with null subtype");
        if (packet.sender == null && !(packet.subtype.equals(TinterPacket.MESSAGE_SUBTYPE_CHAT)
                || packet.subtype.equals(TinterPacket.MESSAGE_SUBTYPE_ANSWER)))
            throw new IllegalPacketException("'message' packet with null sender but subtype " + packet.subtype
                    + " required.");
        if (packet.data == null)
            throw new IllegalPacketException("'message' packet with null data");
        if (!packet.data.isJsonPrimitive())
            throw new IllegalPacketException("'message' packet with non-primitive data: " + packet.data);
        if (!packet.data.getAsJsonPrimitive().isString())
            throw new IllegalPacketException("'message' packet with non-string data: " + packet.data);
        switch (packet.subtype) {
            case TinterPacket.MESSAGE_SUBTYPE_CHAT:
                session.onChatMessage(packet.sender, packet.data.getAsJsonPrimitive().getAsString());
                break;
            case TinterPacket.MESSAGE_SUBTYPE_ANSWER:
                session.onPlayerAnswer(packet.sender, packet.data.getAsJsonPrimitive().getAsString());
                break;
            case TinterPacket.MESSAGE_SUBTYPE_INFO:
                session.onInfoMessage(packet.data.getAsJsonPrimitive().getAsString());
                break;
            case TinterPacket.MESSAGE_SUBTYPE_CURRENT_ANSWER:
                session.onCurrentAnswer(packet.data.getAsJsonPrimitive().getAsString());
                break;
            default:
                throw new IllegalPacketException("'message' packet with unsupported subtype: " + packet.subtype);
        }
    }

}
