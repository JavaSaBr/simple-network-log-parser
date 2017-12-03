package com.ss.simple.network.log.parser.model.log.event;

import org.jetbrains.annotations.NotNull;

/**
 * @author JavaSaBr
 */
public interface LogEvent {

    @NotNull LogEventHeader header();

    @NotNull String value(int index);
}
