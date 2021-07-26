package com.xtex.libtinter.client;

import com.xtex.libtinter.LibTinter;
import com.xtex.libtinter.client.packet.*;
import com.xtex.libtinter.json.PlayerInfo;
import com.xtex.libtinter.json.UserInfo;
import com.xtex.libtinter.TinterPacket;
import com.xtex.libtinter.util.UnsupportedPacketTypeException;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An tinter client session
 */
@SuppressWarnings("unused")
public abstract class TinterClientSession extends WebSocketClient {

    public static final Map<String, IClientPacketHandler> PACKET_HANDLERS = new HashMap<>();

    static {
        PACKET_HANDLERS.put(TinterPacket.PLAYERS_TYPE, new ClientPlayersPacketHandler());
        PACKET_HANDLERS.put(TinterPacket.PLAYER_TYPE, new ClientPlayerPacketHandler());
        PACKET_HANDLERS.put(TinterPacket.SELF_ID_TYPE, new ClientSelfIdPacketHandler());
        PACKET_HANDLERS.put(TinterPacket.MESSAGE_TYPE, new ClientMessagePacketHandler());
        PACKET_HANDLERS.put(TinterPacket.START_TYPE, new ClientStartPacketHandler());
        PACKET_HANDLERS.put(TinterPacket.DRAW_TYPE, new ClientDrawPacketHandler());
        PACKET_HANDLERS.put(TinterPacket.SCORE_TYPE, new ClientScorePacketHandler());
    }

    /**
     * The user info for this session self
     *
     * @see UserInfo
     * @see TinterClientSession#sendHello()
     */
    private final @NotNull UserInfo self;

    /**
     * Create an client session with given info
     *
     * @param serverUri The URI of the server, e.g. <code>ws://127.0.0.1/room</code>
     * @param self      The user info for this session self
     * @see TinterClientSession#self
     * @see WebSocketClient#WebSocketClient(URI)
     */
    public TinterClientSession(@NotNull URI serverUri, @NotNull UserInfo self) {
        super(serverUri);
        this.self = self;
    }

    /**
     * Create an client session with given info
     *
     * @param serverUri     The URI of the server, e.g. <code>ws://127.0.0.1/room</code>
     * @param protocolDraft The protocol draft for <code>WebSocketClient</code>
     * @param self          The user info for this session self
     * @see TinterClientSession#self
     * @see WebSocketClient#WebSocketClient(URI, Draft)
     */
    public TinterClientSession(@NotNull URI serverUri, @NotNull Draft protocolDraft, @NotNull UserInfo self) {
        super(serverUri, protocolDraft);
        this.self = self;
    }

    /**
     * Create an client session with given info
     *
     * @param serverUri   The URI of the server, e.g. <code>ws://127.0.0.1/room</code>
     * @param httpHeaders The http headers for <code>WebSocketClient</code>
     * @param self        The user info for this session self
     * @see TinterClientSession#self
     * @see WebSocketClient#WebSocketClient(URI, Map)
     */
    public TinterClientSession(@NotNull URI serverUri, @NotNull Map<String, String> httpHeaders, @NotNull UserInfo self) {
        super(serverUri, httpHeaders);
        this.self = self;
    }

    /**
     * Create an client session with given info
     *
     * @param serverUri     The URI of the server, e.g. <code>ws://127.0.0.1/room</code>
     * @param protocolDraft The protocol draft for <code>WebSocketClient</code>
     * @param httpHeaders   The http headers for <code>WebSocketClient</code>
     * @param self          The user info for this session self
     * @see TinterClientSession#self
     * @see WebSocketClient#WebSocketClient(URI, Draft, Map)
     */
    public TinterClientSession(@NotNull URI serverUri, @NotNull Draft protocolDraft,
                               @NotNull Map<String, String> httpHeaders, @NotNull UserInfo self) {
        super(serverUri, protocolDraft, httpHeaders);
        this.self = self;
    }

    /**
     * Create an client session with given info
     *
     * @param serverUri      The URI of the server, e.g. <code>ws://127.0.0.1/room</code>
     * @param protocolDraft  The protocol draft for <code>WebSocketClient</code>
     * @param httpHeaders    The http headers for <code>WebSocketClient</code>
     * @param connectTimeout The time-out time for connect for <code>WebSocketClient</code>
     * @param self           The user info for this session self
     * @see TinterClientSession#self
     * @see WebSocketClient#WebSocketClient(URI, Draft, Map, int)
     */
    public TinterClientSession(@NotNull URI serverUri, @NotNull Draft protocolDraft,
                               @NotNull Map<String, String> httpHeaders,
                               @Range(from = 0, to = Integer.MAX_VALUE) int connectTimeout, @NotNull UserInfo self) {
        super(serverUri, protocolDraft, httpHeaders, connectTimeout);
        this.self = self;
    }

    /**
     * Call when the server sent full current player list
     *
     * @param players The list of current player
     * @see TinterClientSession#sendFetchPlayers()
     */
    public abstract void onPlayers(@NotNull List<PlayerInfo> players);

    /**
     * Call when an new player join this room
     *
     * @param player The new player
     */
    public abstract void onAddPlayer(@NotNull PlayerInfo player);

    /**
     * Call when an player leave this room
     *
     * @param player The player that leaf
     */
    public abstract void onRemovePlayer(@NotNull PlayerInfo player);

    /**
     * Call when the server updating the self ID of this session
     * Used to check "should I draw now"
     *
     * @param selfIf The ID of this session
     * @see PlayerInfo#id
     */
    public abstract void onSetSelfId(@NotNull String selfIf);

    /**
     * Call when an player(included self) sent an chat message
     *
     * @param sender  The unique ID of the sender
     * @param message The message that the sender sent
     * @see PlayerInfo#id for <code>sender</code> parameter
     */
    public abstract void onChatMessage(@NotNull String sender, @NotNull String message);

    /**
     * Call when an player(included self) sent an wrong answer
     *
     * @param sender  The unique ID of the sender
     * @param message The message that the sender sent
     * @see PlayerInfo#id for <code>sender</code> parameter
     */
    public abstract void onPlayerAnswer(@NotNull String sender, @NotNull String message);

    /**
     * Call when the server sent a special message to this session
     *
     * @param info The type of the info
     * @see TinterPacket for contants starts with <code>MESSAGE_INFO_</code>
     */
    public abstract void onInfoMessage(@NotNull String info);

    /**
     * Call when the answer be public after an game
     *
     * @param answer The answer of last game
     */
    public abstract void onCurrentAnswer(@NotNull String answer);

    /**
     * Call before <code>onStartGuess</code> and <code>onStartDraw</code>
     * e.g. clear screen and reset timer
     *
     * @see TinterClientSession#onStartGuess(String)
     * @see TinterClientSession#onStartDraw(String)
     */
    public abstract void onStart();

    /**
     * Call after <code>onStart</code> when this session is guesser
     *
     * @param drawerId The unique ID of current drawer
     * @see PlayerInfo#id for <code>drawerId</code> parameter
     * @see TinterClientSession#onStart()
     */
    public abstract void onStartGuess(@NotNull String drawerId);

    /**
     * Call after <code>onStart</code> when this session is drawer
     *
     * @param message The message for drawer like what to draw
     * @see TinterClientSession#onStart()
     */
    public abstract void onStartDraw(@NotNull String message);

    /**
     * Call when adding score for a player
     *
     * @param playerId The unique ID of the player got score
     * @param score    The score need to add
     * @see PlayerInfo#id for <code>playerId</code> parameter
     */
    public abstract void onAddScore(@NotNull String playerId, int score);

    /**
     * Call when updating drawing color on the paint board
     *
     * @param color The color to use
     */
    public abstract void onSetDrawColor(@NotNull String color);

    /**
     * Call when drawing an point on the paint board
     *
     * @param x The X position to draw, scaled in 1280
     * @param y The Y position to draw, scaled in 720
     */
    public abstract void onDrawPoint(int x, int y);

    /**
     * Call when drawing an line from last point(e.g last point, last line to or last eraser)
     *
     * @param x The X position to draw, scaled in 1280
     * @param y The Y position to draw, scaled in 720
     */
    public abstract void onDrawLineTo(int x, int y);

    /**
     * Call when the drawer cleared paint board
     * Color should be ignored
     */
    public abstract void onDrawClear();

    /**
     * Call when the drawer used eraser
     * Color should be ignored
     *
     * @param x The center X position to clear, scaled in 1280
     * @param y The center Y position to clear, scaled in 720
     */
    public abstract void onDrawEraser(int x, int y);

    @Override
    public void onOpen(@NotNull ServerHandshake handshakeData) {
        sendHello();
        sendFetchPlayers();
    }

    @Override
    public void onMessage(@NotNull String message) {
        TinterPacket packet = LibTinter.GSON.fromJson(message, TinterPacket.class);
        IClientPacketHandler handler = PACKET_HANDLERS.get(packet.type);
        if (handler == null)
            throw new UnsupportedPacketTypeException(packet.type, message);
        handler.accept(this, packet);
    }

    /**
     * Send an POJO object packet
     *
     * @param data The POJO object as the data in the packet
     * @see com.google.gson.Gson#toJson(Object)
     */
    public void send(Object data) {
        send(LibTinter.GSON.toJson(data));
    }

    /**
     * Send hello with the <code>self</code> field
     * Automatic called in <code>onOpen</code>
     *
     * @see TinterClientSession#onOpen(ServerHandshake)
     * @see TinterClientSession#sendHello(UserInfo)
     * @see TinterClientSession#send(Object)
     */
    public void sendHello() {
        sendHello(self);
    }

    /**
     * Send hello with an user info to "tell server that who am I"
     *
     * @param user Target user info
     * @see UserInfo
     * @see TinterClientSession#sendHello()
     * @see TinterPacket#createHelloPacket(UserInfo)
     * @see TinterClientSession#send(Object)
     */
    public void sendHello(UserInfo user) {
        send(TinterPacket.createHelloPacket(user));
    }

    /**
     * Send an packet to fetch current players
     * When server response current players list, <code>onPlayers</code> method will be called
     *
     * @see TinterClientSession#onPlayers(List)
     * @see TinterPacket#createFetchPlayersPacket()
     * @see TinterClientSession#send(Object)
     */
    public void sendFetchPlayers() {
        send(TinterPacket.createFetchPlayersPacket());
    }

}
