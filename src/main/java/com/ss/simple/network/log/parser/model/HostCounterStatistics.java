package com.ss.simple.network.log.parser.model;

import static com.ss.rlib.util.ref.ReferenceFactory.newRef;
import com.ss.rlib.concurrent.lock.AsyncReadSyncWriteLock;
import com.ss.rlib.concurrent.lock.LockFactory;
import com.ss.rlib.util.ref.Reference;
import com.ss.rlib.util.ref.ReferenceType;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author JavaSaBr
 */
public class HostCounterStatistics {

    @NotNull
    private final Map<String, Reference> counters;

    @NotNull
    private final AsyncReadSyncWriteLock lock;

    public HostCounterStatistics() {
        this.counters = new TreeMap<>();
        this.lock = LockFactory.newAtomicARSWLock();
    }

    private void incrementCounter(@NotNull final String host, final long count) {
        final Reference counter = counters.computeIfAbsent(host, key -> newRef(ReferenceType.LONG));
        counter.setLong(counter.getLong() + count);
    }

    public void incrementCounters(@NotNull final Map<String, Reference> counters) {
        lock.syncLock();
        try {
            for (final Map.Entry<String, Reference> entry : counters.entrySet()) {
                final String host = entry.getKey();
                final Reference counter = entry.getValue();
                incrementCounter(host, counter.getLong());
            }
        } finally {
            lock.syncUnlock();
        }
    }
}
