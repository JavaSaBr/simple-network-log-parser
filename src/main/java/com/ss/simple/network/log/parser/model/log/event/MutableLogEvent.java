package com.ss.simple.network.log.parser.model.log.event;

import org.jetbrains.annotations.NotNull;

/**
 * @author JavaSaBr
 */
public interface MutableLogEvent extends ReusableLogEvent {

    void setHeader(@NotNull LogEventHeader header);

    void setValues(@NotNull String[] values);
}
