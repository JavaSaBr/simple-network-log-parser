package com.ss.simple.network.log.parser.model.log.event.impl;

import com.ss.rlib.util.ArrayUtils;
import com.ss.rlib.util.StringUtils;
import com.ss.simple.network.log.parser.model.log.event.LogEvent;
import com.ss.simple.network.log.parser.model.log.event.LogEventHeader;
import com.ss.simple.network.log.parser.model.log.event.MutableLogEvent;
import org.jetbrains.annotations.NotNull;

/**
 * The base implementation of {@link LogEvent}.
 *
 * @author JavaSaBr
 */
public class LogEventImpl implements MutableLogEvent {

    /**
     * The header.
     */
    @NotNull
    private LogEventHeader header;

    /**
     * The values.
     */
    @NotNull
    private String[] values;

    public LogEventImpl() {
        this.header = LogEventHeader.EMPTY;
        this.values = StringUtils.EMPTY_ARRAY;
    }

    private LogEventImpl(@NotNull final LogEventHeader header, @NotNull final String[] values) {
        this.header = header;
        this.values = values;
    }

    @Override
    public @NotNull LogEventHeader header() {
        return header;
    }

    @Override
    public @NotNull LogEvent copy() {
        return new LogEventImpl(header, values);
    }

    @Override
    public @NotNull String value(final int index) {
        return values[index];
    }

    @Override
    public void setHeader(@NotNull final LogEventHeader header) {
        this.header = header;
    }

    @Override
    public void setValues(@NotNull final String[] values) {

        if (ArrayUtils.find(values, String::isEmpty) != null) {
            throw new IllegalArgumentException("The values shouldn't contain an empty element.");
        } else if (header.fieldCount() != values.length) {
            throw new IllegalArgumentException("The count of values should be the same as count of field names in the header.");
        }

        this.values = values;
    }

    @Override
    public String toString() {

        final StringBuilder builder = new StringBuilder("LogEvent:\n");

        for (int i = 0; i < values.length; i++) {

            final String value = values[i];
            final String fieldName = header.fieldName(i);

            builder.append('\t').append(fieldName)
                    .append(':').append(value)
                    .append('\n');
        }

        return builder.toString();
    }
}
