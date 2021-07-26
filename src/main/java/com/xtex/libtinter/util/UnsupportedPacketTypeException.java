package com.xtex.libtinter.util;

import org.jetbrains.annotations.NotNull;

/**
 * An exception thrown when the client or server received an unsupported packet
 */
public class UnsupportedPacketTypeException extends RuntimeException {

    public UnsupportedPacketTypeException(@NotNull String type, @NotNull String packet) {
        super(type.concat(" in ").concat(packet));
    }

}
