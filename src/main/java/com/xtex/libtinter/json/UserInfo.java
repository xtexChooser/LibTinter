package com.xtex.libtinter.json;

import com.google.gson.annotations.SerializedName;
import com.xtex.libtinter.TinterPacket;
import org.jetbrains.annotations.NotNull;

/**
 * An user info, only basic info, client send to server to "tell server who am I"
 * For server to client(e.g. synchronize player list), see PlayerInfo
 *
 * @see PlayerInfo
 * @see UserInfo#create(String, String)
 * @see UserInfo#createTest()
 * @see com.xtex.libtinter.client.TinterClientSession#sendHello(UserInfo)
 * @see TinterPacket#createHelloPacket(UserInfo)
 */
@SuppressWarnings("NotNullFieldNotInitialized")
public class UserInfo {

    /**
     * The user name of "me"
     *
     * @see PlayerInfo#username
     */
    @SerializedName("username")
    public @NotNull String username;

    /**
     * The email of "me"
     */
    @SerializedName("email")
    public @NotNull String email;

    /**
     * Create an user info with given user name and email
     *
     * @param username Target user name
     * @param email    Target email
     * @return The user info with given info
     */
    public static @NotNull UserInfo create(@NotNull String username, @NotNull String email) {
        UserInfo info = new UserInfo();
        info.username = username;
        info.email = email;
        return info;
    }

    /**
     * Create an user info for test(random number for user name and empty email, not allowed in deployment server)
     *
     * @return The user info for test
     * @see UserInfo#create(String, String)
     */
    public static @NotNull UserInfo createTest() {
        return create(String.valueOf(Math.random()), "");
    }

}
