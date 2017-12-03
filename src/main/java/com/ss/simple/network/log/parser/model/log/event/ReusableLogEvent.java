package com.ss.simple.network.log.parser.model.log.event;

import org.jetbrains.annotations.NotNull;

/**
 * @author JavaSaBr
 */
public interface ReusableLogEvent extends LogEvent {

    @NotNull LogEvent copy();
}
