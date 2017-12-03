package com.ss.simple.network.log.parser.service.parser;

import com.ss.simple.network.log.parser.service.Service;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.util.Set;

/**
 * The service to parse logs.
 *
 * @author JavaSaBr
 */
public interface ParserService extends Service {

    /**
     * Get the set of available formats to parse.
     *
     * @return the set of available formats to parse.
     */
    @NotNull Set<String> getAvailableFormats();

    /**
     * Parse the log events from the input stream.
     *
     * @param in the input stream.
     * @param format the format.
     */
    void parse(@NotNull InputStream in, @NotNull String format);
}
