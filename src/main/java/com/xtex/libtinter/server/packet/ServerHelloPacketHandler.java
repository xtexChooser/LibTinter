package com.xtex.libtinter.server.packet;

import com.xtex.libtinter.LibTinter;
import com.xtex.libtinter.json.UserInfo;
import com.xtex.libtinter.TinterPacket;
import com.xtex.libtinter.server.TinterServerSession;
import com.xtex.libtinter.util.IllegalPacketException;
import org.java_websocket.WebSocket;

/**
 * Server packet handler for <code>hello</code> packet
 */
public class ServerHelloPacketHandler implements IServerPacketHandler {

    @Override
    public void accept(TinterServerSession server, WebSocket session, TinterPacket packet) {
        if (packet.subtype != null)
            throw new IllegalPacketException("'hello' packet with non-null subtype: " + packet.subtype);
        if (packet.data == null)
            throw new IllegalPacketException("'hello' packet with null data");
        server.onHello(session, LibTinter.GSON.fromJson(packet.data, UserInfo.class));
    }

}
