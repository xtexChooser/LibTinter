package com.xtex.libtinter.client.packet;

import com.xtex.libtinter.client.TinterClientSession;
import com.xtex.libtinter.TinterPacket;
import com.xtex.libtinter.util.IllegalPacketException;

/**
 * Client packet handler for <code>selfId</code> packet
 */
public class ClientSelfIdPacketHandler implements IClientPacketHandler {

    @Override
    public void accept(TinterClientSession session, TinterPacket packet) {
        if (packet.subtype != null)
            throw new IllegalPacketException("'selfId' packet with non-null subtype:" + packet.subtype);
        if (packet.data == null)
            throw new IllegalPacketException("'selfId' packet with null data");
        if (!packet.data.isJsonPrimitive())
            throw new IllegalPacketException("'selfId' packet with non-primitive data" + packet.data);
        if (!packet.data.getAsJsonPrimitive().isString())
            throw new IllegalPacketException("'selfId' packet with non-string data" + packet.data);
        session.onSetSelfId(packet.data.getAsJsonPrimitive().getAsString());
    }

}
