package com.xtex.libtinter.client.packet;

import com.xtex.libtinter.client.TinterClientSession;
import com.xtex.libtinter.TinterPacket;
import com.xtex.libtinter.util.IllegalPacketException;

/**
 * Client packet handler for <code>score</code> packet
 */
public class ClientScorePacketHandler implements IClientPacketHandler {

    @Override
    public void accept(TinterClientSession session, TinterPacket packet) {
        if (packet.subtype != null)
            throw new IllegalPacketException("'score' packet with non-null subtype");
        if (packet.sender == null)
            throw new IllegalPacketException("'score' packet with null sender");
        if (packet.data == null)
            throw new IllegalPacketException("'score' packet with null data");
        if (!packet.data.isJsonPrimitive())
            throw new IllegalPacketException("'score' packet with non-primitive data");
        if (!packet.data.getAsJsonPrimitive().isNumber())
            throw new IllegalPacketException("'score' packet with non-number data");
        session.onAddScore(packet.sender, packet.data.getAsJsonPrimitive().getAsInt());
    }

}
