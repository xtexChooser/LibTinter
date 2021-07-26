package com.xtex.libtinter.server.packet;

import com.xtex.libtinter.TinterPacket;
import com.xtex.libtinter.server.TinterServerSession;
import org.java_websocket.WebSocket;

/**
 * An packet handler for server
 */
@FunctionalInterface
public interface IServerPacketHandler {

    /**
     * Handle a packet received in client with registered type
     *
     * @param server  The server that received this packet
     * @param session The session for the sender
     * @param packet  The packet required to handle
     */
    void accept(TinterServerSession server, WebSocket session, TinterPacket packet);

}
