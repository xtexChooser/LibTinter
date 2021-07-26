package com.xtex.libtinter.server.packet;

import com.xtex.libtinter.TinterPacket;
import com.xtex.libtinter.server.TinterServerSession;
import com.xtex.libtinter.util.IllegalPacketException;
import org.java_websocket.WebSocket;

/**
 * Server packet handler for <code>draw</code> packet
 */
public class ServerDrawPacketHandler implements IServerPacketHandler {

    @Override
    public void accept(TinterServerSession server, WebSocket session, TinterPacket packet) {
        if (packet.subtype == null)
            throw new IllegalPacketException("'draw' packet with null subtype");
        server.onDraw(session, packet.subtype, packet.color, packet.pos);
    }

}
