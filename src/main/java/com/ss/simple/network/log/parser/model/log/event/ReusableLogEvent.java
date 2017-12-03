package com.ss.simple.network.log.parser.model.log.event;

import org.jetbrains.annotations.NotNull;

/**
 * The interface to add a method to make copies of events.
 *
 * @author JavaSaBr
 */
public interface ReusableLogEvent extends LogEvent {

    /**
     * Create a copy of this event.
     *
     * @return the copy.
     */
    @NotNull LogEvent copy();
}
