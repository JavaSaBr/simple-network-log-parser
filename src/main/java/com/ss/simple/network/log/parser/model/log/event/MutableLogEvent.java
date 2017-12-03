package com.ss.simple.network.log.parser.model.log.event;

import org.jetbrains.annotations.NotNull;

/**
 * The interface with write access to log events.
 *
 * @author JavaSaBr
 */
public interface MutableLogEvent extends ReusableLogEvent {

    /**
     * Set the new header.
     *
     * @param header the new header.
     */
    void setHeader(@NotNull LogEventHeader header);

    /**
     * Set the new values.
     *
     * @param values the new values.
     * @throws IllegalArgumentException if you try to set values with null elements or
     * the count if elements isn't correct.
     */
    void setValues(@NotNull String[] values);
}
