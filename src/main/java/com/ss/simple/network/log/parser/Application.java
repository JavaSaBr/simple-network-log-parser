package com.ss.simple.network.log.parser;

import com.ss.rlib.logging.LoggerLevel;
import com.ss.simple.network.log.parser.manager.ControllerManager;
import org.jetbrains.annotations.NotNull;

/**
 * @author JavaSaBr
 */
public class Application {

    public static void main(@NotNull final String[] args) {

        LoggerLevel.INFO.setEnabled(true);
        LoggerLevel.DEBUG.setEnabled(true);

        final ControllerManager controllerManager = ControllerManager.getInstance();
        controllerManager.startControllers();
    }
}
