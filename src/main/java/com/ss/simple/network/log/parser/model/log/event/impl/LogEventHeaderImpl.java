package com.ss.simple.network.log.parser.model.log.event.impl;

import com.ss.rlib.util.ArrayUtils;
import com.ss.simple.network.log.parser.model.log.event.LogEventHeader;
import org.jetbrains.annotations.NotNull;

/**
 * The base implementation of {@link LogEventHeader}.
 *
 * @author JavaSaBr
 */
public class LogEventHeaderImpl implements LogEventHeader {

    /**
     * The field names.
     */
    @NotNull
    private final String[] fieldNames;

    public LogEventHeaderImpl(@NotNull final String[] fieldNames) {
        this.fieldNames = fieldNames;
    }

    @Override
    public @NotNull String fieldName(final int index) {
        return fieldNames[index];
    }

    @Override
    public int fieldNameIndexOf(@NotNull final String fieldName) {
        return ArrayUtils.indexOf(fieldNames, fieldName);
    }

    @Override
    public int fieldCount() {
        return fieldNames.length;
    }
}
