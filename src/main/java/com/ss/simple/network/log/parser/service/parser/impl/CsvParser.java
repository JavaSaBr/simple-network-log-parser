package com.ss.simple.network.log.parser.service.parser.impl;

import com.ss.simple.network.log.parser.model.log.event.LogEventHeader;
import com.ss.simple.network.log.parser.model.log.event.MutableLogEvent;
import com.ss.simple.network.log.parser.model.log.event.ReusableLogEvent;
import com.ss.simple.network.log.parser.model.log.event.impl.LogEventImpl;
import com.ss.simple.network.log.parser.service.parser.Parser;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.util.function.BiConsumer;

/**
 * The implementation of {@link Parser} to parse log events from CSV format.
 *
 * @author JavaSaBr
 */
public class CsvParser implements Parser {

    private static final ThreadLocal<MutableLogEvent> LOCAL_EVENT = ThreadLocal.withInitial(LogEventImpl::new);

    @Override
    public void parse(@NotNull final InputStream in,
                      @NotNull final BiConsumer<@NotNull LogEventHeader, @NotNull ReusableLogEvent> consumer) {

    }

    @Override
    public @NotNull String getFormat() {
        return "CSV";
    }
}
