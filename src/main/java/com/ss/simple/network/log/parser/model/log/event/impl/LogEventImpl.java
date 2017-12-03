package com.ss.simple.network.log.parser.model.log.event.impl;

import static com.ss.rlib.util.ObjectUtils.notNull;
import com.ss.rlib.util.ArrayUtils;
import com.ss.simple.network.log.parser.model.log.event.LogEvent;
import com.ss.simple.network.log.parser.model.log.event.LogEventHeader;
import com.ss.simple.network.log.parser.model.log.event.MutableLogEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author JavaSaBr
 */
public class LogEventImpl implements MutableLogEvent {

    @Nullable
    private LogEventHeader header;

    @Nullable
    private String[] values;

    public LogEventImpl() {
    }

    private LogEventImpl(@NotNull final LogEventHeader header, @NotNull final String[] values) {
        this.header = header;
        this.values = values;
    }

    private @NotNull String[] values() {
        return notNull(values, "Values isn't set.");
    }

    @Override
    public @NotNull LogEventHeader header() {
        return notNull(header, "Header isn't set.");
    }

    @Override
    public @NotNull LogEvent copy() {
        return new LogEventImpl(header(), values());
    }

    @Override
    public @NotNull String value(final int index) {
        return values()[index];
    }

    @Override
    public void setHeader(@NotNull final LogEventHeader header) {
        this.header = header;
    }

    @Override
    public void setValues(@NotNull final String[] values) {

        if (ArrayUtils.find(values, String::isEmpty) != null) {
            throw new IllegalArgumentException("The values contains a null element.");
        }

        this.values = values;
    }
}
