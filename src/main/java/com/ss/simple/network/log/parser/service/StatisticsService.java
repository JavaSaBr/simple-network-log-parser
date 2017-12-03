package com.ss.simple.network.log.parser.service;

import com.ss.rlib.util.ref.Reference;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.BiConsumer;

/**
 * The service to collect and to handle statistics.
 *
 * @author JavaSaBr
 */
public interface StatisticsService extends Service {

    /**
     * Add new statistics with host counters.
     *
     * @param counters the map host name to host counter.
     */
    void addHostCounts(@NotNull Map<String, Reference> counters);

    /**
     * Handle host count statistics.
     *
     * @param consumer the consumer to handle statistics.
     */
    void handleHostCounts(@NotNull BiConsumer<String, Long> consumer);

    /**
     * Clear statistics.
     */
    void clear();
}
