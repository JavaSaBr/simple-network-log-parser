package com.ss.simple.network.log.parser.service.parser.impl.csv;

import com.ss.rlib.util.ArrayUtils;
import com.ss.simple.network.log.parser.model.log.event.LogEventHeader;
import com.ss.simple.network.log.parser.model.log.event.MutableLogEvent;
import com.ss.simple.network.log.parser.model.log.event.ReusableLogEvent;
import com.ss.simple.network.log.parser.model.log.event.impl.LogEventHeaderImpl;
import com.ss.simple.network.log.parser.model.log.event.impl.LogEventImpl;
import com.ss.simple.network.log.parser.service.parser.Parser;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.util.Scanner;
import java.util.function.BiConsumer;

/**
 * The implementation of {@link Parser} to parse log events from CSV format.
 *
 * @author JavaSaBr
 */
public class CsvParser implements Parser {

    private static final CsvReader CSV_READER = CsvReader.getInstance();

    @Override
    public void parse(@NotNull final InputStream in,
                      @NotNull final BiConsumer<@NotNull LogEventHeader, @NotNull ReusableLogEvent> consumer) {

        final MutableLogEvent event = new LogEventImpl();

        LogEventHeader header = null;

        try (final Scanner scanner = new Scanner(in)) {
            while (scanner.hasNextLine()) {

                final String nextLine = scanner.nextLine();

                if (header == null) {
                    if (!nextLine.startsWith("#Fields:")) {
                        continue;
                    }

                    final String fieldsRow = nextLine.substring(8, nextLine.length());
                    final String[] values = CSV_READER.extractValues(fieldsRow, ' ', '"');

                    header = new LogEventHeaderImpl(ArrayUtils.copyOf(values, 0));
                    event.setHeader(header);
                    continue;
                }

                if (nextLine.startsWith("#")) {
                    continue;
                }

                final String[] values = CSV_READER.extractValues(nextLine, ' ', '"');

                event.setValues(ArrayUtils.copyOf(values, 0));

                consumer.accept(header, event);
            }
        }
    }

    @Override
    public @NotNull String getFormat() {
        return "CSV";
    }
}
