package com.ss.simple.network.log.parser.service.parser;

import com.ss.simple.network.log.parser.model.log.event.LogEventHeader;
import com.ss.simple.network.log.parser.model.log.event.ReusableLogEvent;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.util.function.BiConsumer;

/**
 * The interface to implement a parser of log events from different formats.
 *
 * @author JavaSaBr
 */
public interface Parser {

    /**
     * Parse the input stream and handle all log elements using the consumer.
     *
     * @param in the input stream.
     * @param consumer the consumer.
     */
    void parse(@NotNull InputStream in,
               @NotNull BiConsumer<@NotNull LogEventHeader, @NotNull ReusableLogEvent> consumer);

    /**
     * Get the acceptable format.
     *
     * @return the acceptable format.
     */
    @NotNull String getFormat();
}
