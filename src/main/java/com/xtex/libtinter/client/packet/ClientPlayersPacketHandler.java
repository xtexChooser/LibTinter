package com.xtex.libtinter.client.packet;

import com.xtex.libtinter.LibTinter;
import com.xtex.libtinter.client.TinterClientSession;
import com.xtex.libtinter.json.PlayerInfo;
import com.xtex.libtinter.TinterPacket;
import com.xtex.libtinter.util.IllegalPacketException;

/**
 * Client packet handler for <code>players</code> packet
 *
 * @see TinterClientSession#sendFetchPlayers()
 */
public class ClientPlayersPacketHandler implements IClientPacketHandler {

    @Override
    public void accept(TinterClientSession session, TinterPacket packet) {
        if (packet.subtype != null)
            throw new IllegalPacketException("'players' packet with non-null subtype:" + packet.subtype);
        if (packet.data == null)
            throw new IllegalPacketException("'players' packet with null data");
        if (!packet.data.isJsonArray())
            throw new IllegalPacketException("'players' packet with non-array data" + packet.data);
        session.onPlayers(LibTinter.GSON.fromJson(packet.data, PlayerInfo.LIST_TYPE_TOKEN));
    }

}
