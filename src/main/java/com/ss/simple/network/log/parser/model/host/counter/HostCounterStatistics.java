package com.ss.simple.network.log.parser.model.host.counter;

import com.ss.rlib.concurrent.lock.AsyncReadSyncWriteLock;
import com.ss.rlib.concurrent.lock.LockFactory;
import com.ss.rlib.util.array.Array;
import com.ss.rlib.util.array.ArrayFactory;
import com.ss.rlib.util.ref.Reference;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * The container of host counters statistics. This object is thread safe.
 *
 * @author JavaSaBr
 */
public class HostCounterStatistics {

    /**
     * The map host name to host counter.
     */
    @NotNull
    private final Map<String, HostCounter> counters;

    /**
     * The all created host counters;
     */
    @NotNull
    private final Array<HostCounter> hostCounters;

    /**
     * The synchronizator.
     */
    @NotNull
    private final AsyncReadSyncWriteLock lock;

    public HostCounterStatistics() {
        this.counters = new HashMap<>();
        this.hostCounters = ArrayFactory.newArray(HostCounter.class);
        this.lock = LockFactory.newAtomicARSWLock();
    }

    private void incrementCounter(@NotNull final String host, final long count) {
        counters.computeIfAbsent(host, this::makeHostCounter)
                .addCount(count);
    }

    private @NotNull HostCounter makeHostCounter(@NotNull final String host) {
        final HostCounter counter = new HostCounter(host);
        hostCounters.add(counter);
        return counter;
    }

    /**
     * Increment counters.
     *
     * @param counters the map with host counters.
     */
    public void incrementCounters(@NotNull final Map<String, Reference> counters) {
        lock.syncLock();
        try {

            for (final Map.Entry<String, Reference> entry : counters.entrySet()) {
                final String host = entry.getKey();
                final Reference counter = entry.getValue();
                incrementCounter(host, counter.getLong());
            }

            hostCounters.sort(HostCounter::compareTo);

        } finally {
            lock.syncUnlock();
        }
    }

    /**
     * Handle host count statistics.
     *
     * @param consumer the consumer of host count statistics.
     */
    public void handleHostCounts(@NotNull final BiConsumer<String, Long> consumer) {
        lock.asyncLock();
        try {
            hostCounters.forEach(consumer, (counter, cons) ->
                    cons.accept(counter.host(), counter.count()));
        } finally {
            lock.asyncUnlock();
        }
    }
}
