package com.ss.simple.network.log.parser.service;

import com.ss.simple.network.log.parser.manager.ServiceManager;
import org.jetbrains.annotations.NotNull;

/**
 * @author JavaSaB
 */
public interface Service {

    /**
     * Initialize this service.
     *
     * @param serviceManager the service manager.
     */
    default void init(@NotNull final ServiceManager serviceManager) {
    }
}
