package com.xtex.libtinter.server.packet;

import com.xtex.libtinter.TinterPacket;
import com.xtex.libtinter.server.TinterServerSession;
import com.xtex.libtinter.util.IllegalPacketException;
import org.java_websocket.WebSocket;

/**
 * Server packet handler for <code>message</code> packet
 */
public class ServerMessagePacketHandler implements IServerPacketHandler {

    @Override
    public void accept(TinterServerSession server, WebSocket session, TinterPacket packet) {
        if (packet.subtype == null)
            throw new IllegalPacketException("'message' packet with null subtype");
        if (packet.data == null)
            throw new IllegalPacketException("'message' packet with null data");
        if (!packet.data.isJsonPrimitive())
            throw new IllegalPacketException("'message' packet with non-primitive data: " + packet.data);
        if (!packet.data.getAsJsonPrimitive().isString())
            throw new IllegalPacketException("'message' packet with non-string data: " + packet.data);
        switch (packet.subtype) {
            case TinterPacket.MESSAGE_SUBTYPE_CHAT:
                server.onChatMessage(session, packet.data.getAsJsonPrimitive().getAsString());
                break;
            case TinterPacket.MESSAGE_SUBTYPE_ANSWER:
                server.onAnswer(session, packet.data.getAsJsonPrimitive().getAsString());
                break;
            default:
                throw new IllegalPacketException("'message' packet with unsupported subtype: " + packet.subtype);
        }
    }

}
