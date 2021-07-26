package com.xtex.libtinter.client.packet;

import com.xtex.libtinter.client.TinterClientSession;
import com.xtex.libtinter.TinterPacket;
import com.xtex.libtinter.util.IllegalPacketException;

/**
 * Client packet handler for <code>draw</code> packet
 */
public class ClientDrawPacketHandler implements IClientPacketHandler {

    @Override
    public void accept(TinterClientSession session, TinterPacket packet) {
        if (packet.subtype == null)
            throw new IllegalPacketException("'draw' packet with null subtype");
        if (packet.color != null)
            session.onSetDrawColor(packet.color);
        switch (packet.subtype) {
            case TinterPacket.DRAW_SUBTYPE_POINT:
                session.onDrawPoint(packet.pos[0], packet.pos[1]);
                break;
            case TinterPacket.DRAW_SUBTYPE_LINE_TO:
                session.onDrawLineTo(packet.pos[0], packet.pos[1]);
                break;
            case TinterPacket.DRAW_SUBTYPE_CLEAR:
                session.onDrawClear();
                break;
            case TinterPacket.DRAW_SUBTYPE_ERASER:
                session.onDrawEraser(packet.pos[0], packet.pos[1]);
                break;
            default:
                throw new IllegalPacketException("'draw' packet with unsupported subtype: " + packet.subtype);
        }
    }

}
