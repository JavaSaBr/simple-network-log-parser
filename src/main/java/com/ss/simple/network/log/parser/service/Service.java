package com.ss.simple.network.log.parser.service;

import com.ss.simple.network.log.parser.manager.ServiceManager;
import org.jetbrains.annotations.NotNull;

/**
 * @author JavaSaB
 */
public interface Service {

    default void init(@NotNull ServiceManager manager) {
    }
}
