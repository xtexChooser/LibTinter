package com.xtex.libtinter.client.packet;

import com.xtex.libtinter.LibTinter;
import com.xtex.libtinter.client.TinterClientSession;
import com.xtex.libtinter.json.PlayerInfo;
import com.xtex.libtinter.TinterPacket;
import com.xtex.libtinter.util.IllegalPacketException;

/**
 * Client packet handler for <code>player</code> packet
 */
public class ClientPlayerPacketHandler implements IClientPacketHandler {

    @Override
    public void accept(TinterClientSession session, TinterPacket packet) {
        if (packet.subtype == null)
            throw new IllegalPacketException("'player' packet with null subtype");
        if (packet.data == null)
            throw new IllegalPacketException("'player' packet with null data");
        PlayerInfo playerInfo = LibTinter.GSON.fromJson(packet.data, PlayerInfo.class);
        switch (packet.subtype) {
            case TinterPacket.PLAYER_SUBTYPE_ADD:
                session.onAddPlayer(playerInfo);
                break;
            case TinterPacket.PLAYER_SUBTYPE_REMOVE:
                session.onRemovePlayer(playerInfo);
                break;
            default:
                throw new IllegalPacketException("'player' packet with unsupported subtype: " + packet.subtype);
        }
    }

}
