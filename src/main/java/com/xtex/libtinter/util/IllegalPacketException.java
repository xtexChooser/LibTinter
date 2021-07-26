package com.xtex.libtinter.util;

import org.jetbrains.annotations.NotNull;

/**
 * An exception thrown by packet handler when received an illegal packet
 */
public class IllegalPacketException extends RuntimeException {

    public IllegalPacketException(@NotNull String message) {
        super(message);
    }

    public IllegalPacketException(@NotNull String message, @NotNull Throwable cause) {
        super(message, cause);
    }

    public IllegalPacketException(@NotNull Throwable cause) {
        super(cause);
    }

}
