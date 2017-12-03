package com.ss.simple.network.log.parser.model.log.event;

import org.jetbrains.annotations.NotNull;

/**
 * @author JavaSaBr
 */
public interface LogEventHeader {

    @NotNull String field(int index);

    int fieldIndexOf(@NotNull String fieldName);

    int fieldCount();
}
