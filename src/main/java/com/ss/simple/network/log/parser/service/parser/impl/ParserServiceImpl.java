package com.ss.simple.network.log.parser.service.parser.impl;

import static com.ss.rlib.util.ObjectUtils.notNull;
import static com.ss.rlib.util.ref.ReferenceFactory.newRef;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import com.ss.rlib.util.ClassUtils;
import com.ss.rlib.util.ref.Reference;
import com.ss.rlib.util.ref.ReferenceType;
import com.ss.simple.network.log.parser.manager.ClasspathManager;
import com.ss.simple.network.log.parser.manager.ServiceManager;
import com.ss.simple.network.log.parser.service.StatisticsService;
import com.ss.simple.network.log.parser.service.parser.Parser;
import com.ss.simple.network.log.parser.service.parser.ParserService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author JavaSaBr
 */
public class ParserServiceImpl implements ParserService {

    @NotNull
    private final List<Parser> parsers;

    @NotNull
    private final Map<String, Parser> formatToParser;

    @Nullable
    private StatisticsService statisticsService;

    public ParserServiceImpl() {
        final ClasspathManager classpathManager = ClasspathManager.getInstance();
        this.parsers = classpathManager.findImplementations(Parser.class).stream()
                .map(ClassUtils::newInstance)
                .map(Parser.class::cast)
                .collect(toList());
        this.formatToParser = parsers.stream()
                .collect(toMap(Parser::getFormat, parser -> parser));
    }

    @Override
    public void init(@NotNull final ServiceManager manager) {
        this.statisticsService = manager.getService(StatisticsService.class);
    }

    private @NotNull StatisticsService getStatisticsService() {
        return notNull(statisticsService, "Statistics Service isn't set.");
    }

    @Override
    public @NotNull Set<String> getAvailableFormats() {
        return formatToParser.keySet();
    }

    @Override
    public void parse(@NotNull final InputStream in, @NotNull final String format) {

        final Parser parser = formatToParser.get(format);
        if (parser == null) {
            throw new IllegalArgumentException("Unknown format " + format);
        }

        final Reference ref = newRef(ReferenceType.INTEGER);
        ref.setInt(-1);

        final Map<String, Reference> counters = new TreeMap<>();

        parser.parse(in, (header, event) -> {

            if (ref.getInt() == -1) {
                ref.setInt(header.fieldIndexOf("host"));
            }

            final String value = event.value(ref.getInt());
            final Reference counter = counters.computeIfAbsent(value,
                    key -> newRef(ReferenceType.LONG));

            counter.setLong(counter.getLong() + 1);
        });

        final StatisticsService statisticsService = getStatisticsService();
        statisticsService.addHostCounts(counters);
    }
}
