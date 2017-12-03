package com.ss.simple.network.log.parser.model.host.counter;

import org.jetbrains.annotations.NotNull;

/**
 * The class to present a count of a host.
 *
 * @author JavaSaBr
 */
public class HostCounter implements Comparable<HostCounter> {

    /**
     * The host.
     */
    @NotNull
    private final String host;

    /**
     * The current count.
     */
    private long count;

    public HostCounter(@NotNull final String host) {
        this.host = host;
        this.count = 0;
    }

    /**
     * @return the host.
     */
    public @NotNull String host() {
        return host;
    }

    /**
     * @return the current count.
     */
    public long count() {
        return count;
    }

    /**
     * Add the value to the current count.
     *
     * @param added the added value.
     */
    public void addCount(final long added) {
        this.count += added;
    }

    @Override
    public int compareTo(@NotNull final HostCounter other) {
        return Long.compare(other.count, count);
    }
}
