package com.ss.simple.network.log.parser.model.log.event;

import org.jetbrains.annotations.NotNull;

/**
 * The interface to present a header of log events.
 *
 * @author JavaSaBr
 */
public interface LogEventHeader {

    @NotNull LogEventHeader EMPTY = new LogEventHeader(){};

    /**
     * Get the field name by the index.
     *
     * @param index the index.
     * @return the field name.
     */
    default @NotNull String fieldName(int index) {
        throw new UnsupportedOperationException();
    }

    /**
     * Get an index of field by the name.
     *
     * @param fieldName the field name.
     * @return the index or -1.
     */
    default int fieldNameIndexOf(@NotNull String fieldName) {
        return -1;
    }

    /**
     * Get the count of fields.
     *
     * @return the count of fields.
     */
    default int fieldCount() {
        return 0;
    }
}
