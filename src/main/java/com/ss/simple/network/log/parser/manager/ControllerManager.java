package com.ss.simple.network.log.parser.manager;

import static java.util.stream.Collectors.toList;
import com.ss.rlib.util.ClassUtils;
import com.ss.simple.network.log.parser.controller.Controller;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * The manager to manage all controllers.
 *
 * @author JavaSaBr
 */
public class ControllerManager {

    @NotNull
    private static final ControllerManager INSTANCE = new ControllerManager();

    public static @NotNull ControllerManager getInstance() {
        return INSTANCE;
    }

    /**
     * The list of created controllers.
     */
    @NotNull
    private final List<Controller> controllers;

    private ControllerManager() {
        final ClasspathManager classpathManager = ClasspathManager.getInstance();
        this.controllers = classpathManager.findImplementations(Controller.class).stream()
                .map(ClassUtils::newInstance)
                .map(Controller.class::cast)
                .collect(toList());
    }

    /**
     * Start all controllers.
     */
    public void startControllers() {
        controllers.forEach(Controller::start);
    }

    /**
     * Stop all controllers.
     */
    public void stopControllers() {
        controllers.forEach(Controller::start);
    }
}
