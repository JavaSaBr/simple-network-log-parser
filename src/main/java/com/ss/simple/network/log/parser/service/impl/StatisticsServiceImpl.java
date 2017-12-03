package com.ss.simple.network.log.parser.service.impl;

import com.ss.rlib.util.ref.Reference;
import com.ss.simple.network.log.parser.model.HostCounterStatistics;
import com.ss.simple.network.log.parser.service.StatisticsService;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * @author JavaSaBr
 */
public class StatisticsServiceImpl implements StatisticsService {

    @NotNull
    private final HostCounterStatistics hostCounterStatistics;

    public StatisticsServiceImpl() {
        this.hostCounterStatistics = new HostCounterStatistics();
    }

    @Override
    public void addHostCounts(@NotNull final Map<String, Reference> counters) {
        hostCounterStatistics.incrementCounters(counters);
    }
}