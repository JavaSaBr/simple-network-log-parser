package com.ss.simple.network.log.parser.manager;

import static java.util.stream.Collectors.toList;
import com.ss.rlib.util.ClassUtils;
import com.ss.simple.network.log.parser.controller.Controller;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author JavaSaBr
 */
public class ControllerManager {

    @NotNull
    private static final ControllerManager INSTANCE = new ControllerManager();

    public static @NotNull ControllerManager getInstance() {
        return INSTANCE;
    }

    @NotNull
    private final List<Controller> controllers;

    private ControllerManager() {
        final ClasspathManager classpathManager = ClasspathManager.getInstance();
        this.controllers = classpathManager.findImplementations(Controller.class).stream()
                .map(ClassUtils::newInstance)
                .map(Controller.class::cast)
                .collect(toList());
    }

    public void startControllers() {
        controllers.forEach(Controller::start);
    }

    public void stopControllers() {
        controllers.forEach(Controller::start);
    }
}
