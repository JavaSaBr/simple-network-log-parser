package com.ss.simple.network.log.parser.service.parser.impl.csv;

import com.ss.rlib.util.array.Array;
import com.ss.rlib.util.array.ArrayFactory;
import com.ss.rlib.util.array.UnsafeArray;
import org.jetbrains.annotations.NotNull;

/**
 * @author JavaSaBr
 */
public class CsvReader {

    @NotNull
    private static final ThreadLocal<StringBuilder> LOCAL_BUILDER = ThreadLocal.withInitial(StringBuilder::new);

    @NotNull
    private static final ThreadLocal<Array<String>> LOCAL_ARRAY = ThreadLocal.withInitial(() -> ArrayFactory.newArray(String.class));

    @NotNull
    private static final ThreadLocal<char[]> LOCAL_CHARS = ThreadLocal.withInitial(() -> new char[1024]);

    @NotNull
    private static final CsvReader INSTANCE = new CsvReader();

    public static @NotNull CsvReader getInstance() {
        return INSTANCE;
    }

    public String[] extractValues(@NotNull final String csvRow, final char splitter, final char wrapper) {

        final StringBuilder builder = clear(LOCAL_BUILDER.get());
        final char[] chars = getChars(csvRow);

        final UnsafeArray<String> result = LOCAL_ARRAY.get().asUnsafe();
        result.clear();

        boolean ignoreSplitters = false;

        for (int i = 0, length = csvRow.length(); i < length; i++) {

            final char ch = chars[i];

            if (ch == splitter && !ignoreSplitters) {
                addValue(result, builder);
                continue;
            }

            if (ch == wrapper) {
                ignoreSplitters = !ignoreSplitters;
                continue;
            }

            builder.append(ch);
        }

        addValue(result, builder);

        result.trimToSize();
        return result.array();
    }

    private void addValue(@NotNull final Array<String> container, @NotNull final StringBuilder builder) {
        if (builder.length() > 0) {
            container.add(builder.toString());
            clear(builder);
        }
    }

    private @NotNull StringBuilder clear(@NotNull final StringBuilder builder) {
        if (builder.length() > 0) {
            builder.delete(0, builder.length());
        }
        return builder;
    }

    private @NotNull char[] getChars(@NotNull final String csvRow) {

        char[] chars = LOCAL_CHARS.get();

        if (csvRow.length() > chars.length) {
            chars = new char[csvRow.length() * 2];
            LOCAL_CHARS.set(chars);
        }

        csvRow.getChars(0, csvRow.length(), chars, 0);

        return chars;
    }
}
