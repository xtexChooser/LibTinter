package com.xtex.libtinter.client.packet;

import com.xtex.libtinter.client.TinterClientSession;
import com.xtex.libtinter.TinterPacket;
import com.xtex.libtinter.util.IllegalPacketException;

/**
 * Client packet handler for <code>start</code> packet
 */
public class ClientStartPacketHandler implements IClientPacketHandler {

    @Override
    public void accept(TinterClientSession session, TinterPacket packet) {
        if (packet.subtype == null)
            throw new IllegalPacketException("'start' packet with null subtype");
        if (packet.data == null)
            throw new IllegalPacketException("'start' packet with null data");
        if (!packet.data.isJsonPrimitive())
            throw new IllegalPacketException("'start' packet with non-primitive data");
        if (!packet.data.getAsJsonPrimitive().isString())
            throw new IllegalPacketException("'start' packet with non-string data");
        session.onStart();
        String data = packet.data.getAsJsonPrimitive().getAsString();
        switch (packet.subtype) {
            case TinterPacket.START_SUBTYPE_GUESS:
                session.onStartGuess(data);
                break;
            case TinterPacket.START_SUBTYPE_DRAW:
                session.onStartDraw(data);
                break;
            default:
                throw new IllegalPacketException("'message' packet with unsupported subtype: " + packet.subtype);
        }
    }

}
