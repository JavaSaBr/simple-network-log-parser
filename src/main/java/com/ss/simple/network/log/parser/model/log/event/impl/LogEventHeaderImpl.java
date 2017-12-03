package com.ss.simple.network.log.parser.model.log.event.impl;

import com.ss.rlib.util.ArrayUtils;
import com.ss.simple.network.log.parser.model.log.event.LogEventHeader;
import org.jetbrains.annotations.NotNull;

/**
 * @author JavaSaBr
 */
public class LogEventHeaderImpl implements LogEventHeader {

    @NotNull
    private final String[] fields;

    public LogEventHeaderImpl(@NotNull final String[] fields) {
        this.fields = fields;
    }

    @Override
    public @NotNull String field(final int index) {
        return fields[index];
    }

    @Override
    public int fieldIndexOf(@NotNull final String fieldName) {
        return ArrayUtils.indexOf(fields, fieldName);
    }

    @Override
    public int fieldCount() {
        return fields.length;
    }
}
