package com.xtex.libtinter;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import com.xtex.libtinter.json.PlayerInfo;
import com.xtex.libtinter.json.UserInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Tinter packet base
 * Defined basic properties in all the packets
 */
@SuppressWarnings({"unused", "NotNullFieldNotInitialized"})
public class TinterPacket {

    public static final String HELLO_TYPE = "hello";

    public static final String FETCH_PLAYERS_TYPE = "fetch_players";

    public static final String PLAYERS_TYPE = "players";

    public static final String PLAYER_TYPE = "player";
    public static final String PLAYER_SUBTYPE_ADD = "add";
    public static final String PLAYER_SUBTYPE_REMOVE = "remove";

    public static final String SELF_ID_TYPE = "selfId";

    public static final String MESSAGE_TYPE = "message";
    public static final String MESSAGE_SUBTYPE_CHAT = "chat";
    public static final String MESSAGE_SUBTYPE_ANSWER = "answer";

    /**
     * Only server to client
     */
    public static final String MESSAGE_SUBTYPE_INFO = "info";

    /**
     * Only server to client
     */
    public static final String MESSAGE_SUBTYPE_CURRENT_ANSWER = "currentAnswer";

    /**
     * Known as <code>游戏未开始</code>
     */
    public static final String MESSAGE_INFO_GAME_NOT_START = "E_NOT_START";

    /**
     * Known as <code>你已经猜出来了</code>
     */
    public static final String MESSAGE_INFO_ALREADY_SUCCESS = "E_SUCCESS";

    /**
     * Known as <code>想泄题吗(</code>
     */
    public static final String MESSAGE_INFO_DRAWER = "E_DRAW";

    /**
     * Known as <code>本轮已结束(</code>
     */
    public static final String MESSAGE_INFO_FINISHED = "E_FINISHED";

    /**
     * Known as <code>不许发答案</code>
     */
    public static final String MESSAGE_INFO_SEND_ANSWER = "E_SEND_ANSWER";

    /**
     * Known as <code>请先设置 email</code>
     */
    public static final String MESSAGE_INFO_NO_EMAIL = "E_NO_EMAIL";

    /**
     * Known as <code>猜对了</code>
     */
    public static final String MESSAGE_INFO_ANSWER_CORRECT = "CORRECT";

    public static final String START_TYPE = "start";
    public static final String START_SUBTYPE_GUESS = "guess";
    public static final String START_SUBTYPE_DRAW = "draw";

    public static final String DRAW_TYPE = "draw";
    public static final String DRAW_SUBTYPE_POINT = "point";
    public static final String DRAW_SUBTYPE_LINE_TO = "lineTo";
    public static final String DRAW_SUBTYPE_CLEAR = "clear";
    public static final String DRAW_SUBTYPE_ERASER = "eraser";

    public static final String SCORE_TYPE = "score";

    public static final String SKIP_TYPE = "skip";

    /**
     * Packet type
     */
    @SerializedName("type")
    public @NotNull String type;

    /**
     * Packet sub-type
     */
    @SerializedName("subtype")
    public @Nullable String subtype;

    /**
     * The unique ID of the sender of this packet
     *
     * @see com.xtex.libtinter.json.PlayerInfo#id
     */
    @SerializedName("sender")
    public @Nullable String sender;

    /**
     * Packet data
     */
    @SerializedName("data")
    public @Nullable JsonElement data;

    /**
     * Color for drawing
     * Null to use last color
     */
    @SerializedName("color")
    public @Nullable String color;

    /**
     * Position for drawing
     */
    @SerializedName("pos")
    public int[] pos;

    /**
     * Create an packet with given type, subtype, sender and JSON data
     *
     * @param type    The type of this packet
     * @param subtype The subtype of this packet
     * @param sender  The sender of this packet
     * @param data    The data of this packet
     * @return Created packet
     */
    public static TinterPacket create(@NotNull String type, @Nullable String subtype, @Nullable String sender,
                                      @Nullable JsonElement data) {
        TinterPacket packet = new TinterPacket();
        packet.type = type;
        packet.subtype = subtype;
        packet.sender = sender;
        packet.data = data;
        return packet;
    }

    /**
     * Create an packet with given type, subtype and POJO data
     *
     * @param type    The type of this packet
     * @param subtype The subtype of this packet
     * @param sender  The sender of this packet
     * @param data    The data of this packet
     * @return Created packet
     * @see com.google.gson.Gson#toJsonTree(Object)
     */
    public static TinterPacket create(@NotNull String type, @Nullable String subtype, @Nullable String sender,
                                      @NotNull Object data) {
        return create(type, subtype, sender, LibTinter.GSON.toJsonTree(data));
    }

    /**
     * Create an draw packet
     *
     * @param subtype The subtype of this packet
     * @param color   The color
     * @param pos     The pos
     * @return Created draw packet
     */
    public static TinterPacket createDraw(@NotNull String subtype, @Nullable String color, int[] pos) {
        TinterPacket packet = new TinterPacket();
        packet.type = DRAW_TYPE;
        packet.subtype = subtype;
        packet.color = color;
        packet.pos = pos;
        return packet;
    }

    /**
     * Create an hello packet to "tell server who am I"
     *
     * @param user The user info "for me"
     * @return Created packet
     */
    public static TinterPacket createHelloPacket(@NotNull UserInfo user) {
        return create(HELLO_TYPE, null, null, user);
    }

    /**
     * Create a packet to requested current player list
     *
     * @return Created packet
     */
    public static TinterPacket createFetchPlayersPacket() {
        return create(FETCH_PLAYERS_TYPE, null, null, null);
    }

    /**
     * Create a packet to update full players list, should be sent by server after received an fetch player packet
     *
     * @param player The player list
     * @return Created packet
     */
    public static TinterPacket createPlayersPacket(List<PlayerInfo> player) {
        return create(PLAYERS_TYPE, null, null, player);
    }

    /**
     * Create a packet to add or remove player in player list
     *
     * @param subtype <code>PLAYER_SUBTYPE_ADD</code> or <code>PLAYER_SUBTYPE_REMOVE</code>
     * @param player  The player need to add or remove
     * @return Created packet
     */
    public static TinterPacket createPlayerPacket(String subtype, PlayerInfo player) {
        return create(PLAYER_TYPE, subtype, null, player);
    }

    /**
     * Create a packet to add player to player list
     *
     * @param player The player need to add
     * @return Created packet
     */
    public static TinterPacket createAddPlayerPacket(PlayerInfo player) {
        return create(PLAYER_TYPE, PLAYER_SUBTYPE_ADD, null, player);
    }

    /**
     * Create a packet to remove player from player list
     *
     * @param player The player need to remove
     * @return Created packet
     */
    public static TinterPacket createRemovePlayerPacket(PlayerInfo player) {
        return create(PLAYER_TYPE, PLAYER_SUBTYPE_REMOVE, null, player);
    }

    /**
     * Create a packet to update the self ID for client
     *
     * @param selfId The self ID for client need to set
     * @return Created packet
     */
    public static TinterPacket createSelfIdPacket(String selfId) {
        return create(SELF_ID_TYPE, null, null, selfId);
    }

    /**
     * Create a packet to show an message on client
     *
     * @param subtype The message type, see constants starts with <code>MESSAGE_SUBTYPE_</code>
     * @param sender  The unique ID of the sender, null when client send to server
     * @param message The message to show, see constants start with <code>MESSAGE_INFO_</code>
     * @return Created packet
     */
    public static TinterPacket createMessagePacket(String subtype, @Nullable String sender, String message) {
        return create(MESSAGE_TYPE, subtype, null, message);
    }

    /**
     * Create a packet to start game on client
     *
     * @param subtype The type of this packet
     * @param data    The data for client, drawer ID for guess and drawing content message for drawer
     * @return Created packet
     */
    public static TinterPacket createStartPacket(String subtype, String data) {
        return create(START_TYPE, subtype, null, data);
    }

    /**
     * Create a packet to start game on server
     *
     * @return Created packet
     */
    public static TinterPacket createStartPacket() {
        return createStartPacket(null, null);
    }

    /**
     * Create a packet to start guess on client
     *
     * @param drawerId The unique ID for drawer
     * @return Created packet
     */
    public static TinterPacket createStartGuessPacket(String drawerId) {
        return createStartPacket(START_SUBTYPE_GUESS, drawerId);
    }

    /**
     * Create a packet to start draw on client
     *
     * @param content The content to draw
     * @return Created packet
     */
    public static TinterPacket createStartDrawPacket(String content) {
        return createStartPacket(START_SUBTYPE_DRAW, content);
    }

    /**
     * Create a packet to add score for an player
     *
     * @param playerId The unique ID of the player that got score
     * @param score    The score that the player got
     * @return Created packet
     */
    public static TinterPacket createAddScorePacket(String playerId, int score) {
        return create(SCORE_TYPE, null, playerId, score);
    }

    /**
     * Create a packet to skip the round drawing
     *
     * @return Created packet
     */
    public static TinterPacket createSkipPacket() {
        return create(SKIP_TYPE, null, null, null);
    }

}
