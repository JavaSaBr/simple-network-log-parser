package com.ss.simple.network.log.parser.model.log.event;

import org.jetbrains.annotations.NotNull;

/**
 * The interface to present a log event.
 *
 * @author JavaSaBr
 */
public interface LogEvent {

    /**
     * Get the header of this event.
     *
     * @return the header of this event.
     */
    @NotNull LogEventHeader header();

    /**
     * Get value of a field by the index.
     *
     * @param index the index.
     * @return the value.
     */
    @NotNull String value(int index);
}
