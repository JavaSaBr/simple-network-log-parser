package com.ss.simple.network.log.parser.service.parser.impl.csv;

import com.ss.rlib.util.ArrayUtils;
import com.ss.rlib.util.StringUtils;
import com.ss.simple.network.log.parser.model.log.event.LogEventHeader;
import com.ss.simple.network.log.parser.model.log.event.MutableLogEvent;
import com.ss.simple.network.log.parser.model.log.event.ReusableLogEvent;
import com.ss.simple.network.log.parser.model.log.event.impl.LogEventHeaderImpl;
import com.ss.simple.network.log.parser.model.log.event.impl.LogEventImpl;
import com.ss.simple.network.log.parser.service.parser.Parser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.BiConsumer;

/**
 * The implementation of {@link Parser} to parse log events from CSV format.
 *
 * @author JavaSaBr
 */
public class CsvParser implements Parser {

    @NotNull
    private static final CsvReader CSV_READER = CsvReader.getInstance();

    private static final ThreadLocal<char[]> LOCAL_FIRST_CHAR_ARRAY = ThreadLocal.withInitial(() -> new char[4098]);
    private static final ThreadLocal<char[]> LOCAL_SECOND_CHAR_ARRAY = ThreadLocal.withInitial(() -> new char[4098]);

    @Override
    public void parse(@NotNull final InputStream in,
                      @NotNull final BiConsumer<@NotNull LogEventHeader, @NotNull ReusableLogEvent> consumer) {

        char[] firstChars = LOCAL_FIRST_CHAR_ARRAY.get();
        char[] secondChars = LOCAL_SECOND_CHAR_ARRAY.get();

        int charsOffset = 0;
        int readChars;

        // we use one instance to present all rows
        final MutableLogEvent event = new LogEventImpl();

        LogEventHeader header = null;

        try (final InputStreamReader reader = new InputStreamReader(in, "UTF-8")) {
            while (true) {

                readChars = reader.read(firstChars, charsOffset, firstChars.length - charsOffset);
                final int totalRead = charsOffset + readChars;

                final String nextLine = extractLine(firstChars, totalRead);
                if (StringUtils.isEmpty(nextLine)) {

                    if (charsOffset == firstChars.length) {
                        throw new IllegalArgumentException("The log contains too long lines.");
                    }

                    if (readChars == -1) {
                        return;
                    }

                    charsOffset += readChars;
                    continue;
                }

                // we need +1 to skip next line symbol
                System.arraycopy(firstChars, nextLine.length() + 1, secondChars, 0, totalRead - (nextLine.length() + 1));

                final char[] temp = firstChars;
                firstChars = secondChars;
                secondChars = temp;

                charsOffset = totalRead - (nextLine.length() + 1);

                // at first, we should find a row with list of fields.
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

                // it seems it's just a description row, we can skip it.
                if (nextLine.startsWith("#")) {
                    continue;
                }

                final String[] values = CSV_READER.extractValues(nextLine, ' ', '"');

                // reuse the event instance
                event.setValues(ArrayUtils.copyOf(values, 0));

                consumer.accept(header, event);
            }
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private @Nullable String extractLine(@NotNull final char[] chars, final int readChars) {

        for (int i = 0; i < readChars; i++) {
            final char ch = chars[i];
            if (ch == '\n') {
                return String.valueOf(chars, 0, i);
            }
        }

        return null;
    }

    @Override
    public @NotNull String getFormat() {
        return "CSV";
    }
}
