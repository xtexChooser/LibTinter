package com.xtex.libtinter.server.packet;

import com.xtex.libtinter.TinterPacket;
import com.xtex.libtinter.server.TinterServerSession;
import com.xtex.libtinter.util.IllegalPacketException;
import org.java_websocket.WebSocket;

/**
 * Server packet handler for <code>skip</code> packet
 */
public class ServerSkipPacketHandler implements IServerPacketHandler {

    @Override
    public void accept(TinterServerSession server, WebSocket session, TinterPacket packet) {
        if (packet.subtype != null)
            throw new IllegalPacketException("'skip' packet with non-null subtype: " + packet.subtype);
        server.onSkipRound(session);
    }

}
