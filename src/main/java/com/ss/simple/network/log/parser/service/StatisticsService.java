package com.ss.simple.network.log.parser.service;

import com.ss.rlib.util.ref.Reference;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * @author JavaSaBr
 */
public interface StatisticsService extends Service {

    void addHostCounts(@NotNull Map<String, Reference> counters);
}
