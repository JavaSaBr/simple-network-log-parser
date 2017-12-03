package com.ss.simple.network.log.parser.model.log.event;

import org.jetbrains.annotations.NotNull;

/**
 * @author JavaSaBr
 */
public interface LogEventHeader {

    @NotNull LogEventHeader EMPTY = new LogEventHeader() {};

    default @NotNull String field(int index) {
        throw new UnsupportedOperationException();
    }

    default int fieldIndexOf(@NotNull String fieldName) {
        return -1;
    }

    default int fieldCount() {
        return 0;
    }
}
