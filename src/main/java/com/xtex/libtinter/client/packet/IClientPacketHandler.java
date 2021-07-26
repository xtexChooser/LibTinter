package com.xtex.libtinter.client.packet;

import com.xtex.libtinter.client.TinterClientSession;
import com.xtex.libtinter.TinterPacket;

/**
 * An packet handler for client
 */
@FunctionalInterface
public interface IClientPacketHandler {

    /**
     * Handle a packet received in client with registered type
     *
     * @param session The client session that received this packet
     * @param packet  The packet required to handle
     */
    void accept(TinterClientSession session, TinterPacket packet);

}
