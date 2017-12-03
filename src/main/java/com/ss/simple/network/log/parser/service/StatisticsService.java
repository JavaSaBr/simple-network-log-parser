package com.ss.simple.network.log.parser.service;

import com.ss.rlib.util.ref.Reference;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @author JavaSaBr
 */
public interface StatisticsService extends Service {

    void addHostCounts(@NotNull Map<String, Reference> counters);

    void handleHostCounts(@NotNull BiConsumer<String, Long> consumer);
}
