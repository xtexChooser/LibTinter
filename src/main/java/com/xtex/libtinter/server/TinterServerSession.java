package com.xtex.libtinter.server;

import com.xtex.libtinter.LibTinter;
import com.xtex.libtinter.TinterPacket;
import com.xtex.libtinter.json.UserInfo;
import com.xtex.libtinter.server.packet.*;
import com.xtex.libtinter.util.UnsupportedPacketTypeException;
import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.server.WebSocketServer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An tinter server session
 */
@SuppressWarnings("unused")
public abstract class TinterServerSession extends WebSocketServer {

    public static final Map<String, IServerPacketHandler> PACKET_HANDLERS = new HashMap<>();

    static {
        PACKET_HANDLERS.put(TinterPacket.HELLO_TYPE, new ServerHelloPacketHandler());
        PACKET_HANDLERS.put(TinterPacket.DRAW_TYPE, new ServerDrawPacketHandler());
        PACKET_HANDLERS.put(TinterPacket.MESSAGE_TYPE, new ServerMessagePacketHandler());
        PACKET_HANDLERS.put(TinterPacket.START_TYPE, new ServerStartPacketHandler());
        PACKET_HANDLERS.put(TinterPacket.SKIP_TYPE, new ServerSkipPacketHandler());
    }

    public TinterServerSession() {
        super();
    }

    public TinterServerSession(@NotNull InetSocketAddress address) {
        super(address);
    }

    public TinterServerSession(@NotNull InetSocketAddress address, int decoderCount) {
        super(address, decoderCount);
    }

    public TinterServerSession(@NotNull InetSocketAddress address, @NotNull List<Draft> drafts) {
        super(address, drafts);
    }

    public TinterServerSession(@NotNull InetSocketAddress address, int decoderCount, @NotNull List<Draft> drafts) {
        super(address, decoderCount, drafts);
    }

    public TinterServerSession(@NotNull InetSocketAddress address, int decoderCount, @NotNull List<Draft> drafts,
                               @NotNull Collection<WebSocket> connectionsContainer) {
        super(address, decoderCount, drafts, connectionsContainer);
    }

    /**
     * Call when the client sent a hello packet after the client connected to server
     *
     * @param session The connection of the client
     * @param info    The user info from client
     */
    public abstract void onHello(@NotNull WebSocket session, @NotNull UserInfo info);

    /**
     * Call when the client sent a draw packet when the client drawing
     * Check is the player drawer and broadcast to other players
     *
     * @param session The connection of the client
     * @param type    The drawing operate type
     * @param color   The color drawing, non-null to change ignoring type, null for non-changed
     * @param pos     The position to draw, { x, y }
     */
    public abstract void onDraw(@NotNull WebSocket session, @NotNull String type, @Nullable String color, int[] pos);

    /**
     * Call when an client sent an chat message
     * Broadcast this message with sender to other clients
     *
     * @param session The connection of the client
     * @param message The message from the client
     */
    public abstract void onChatMessage(@NotNull WebSocket session, @NotNull String message);

    /**
     * Call when an client sent an answer
     * Broadcast this answer with sender to other clients if the answer is wrong
     * and send <code>MESSAGE_INFO_ANSWER_CORRECT</code> and add score if the answer is correct
     *
     * @param session The connection of the client
     * @param answer  The answer from the client
     */
    public abstract void onAnswer(@NotNull WebSocket session, @NotNull String answer);

    /**
     * Call when an client try to start game
     *
     * @param session The connection of the client
     */
    public abstract void onStartGame(@NotNull WebSocket session);

    /**
     * Call when an client try to skip this round
     *
     * @param session The connection of the client
     */
    public abstract void onSkipRound(@NotNull WebSocket session);

    @Override
    public void onMessage(@NotNull WebSocket conn, @NotNull String message) {
        TinterPacket packet = LibTinter.GSON.fromJson(message, TinterPacket.class);
        IServerPacketHandler handler = PACKET_HANDLERS.get(packet.type);
        if (handler == null)
            throw new UnsupportedPacketTypeException(packet.type, message);
        handler.accept(this, conn, packet);
    }

}
