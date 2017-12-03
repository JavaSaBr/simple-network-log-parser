package com.ss.simple.network.log.parser.manager;

import static java.util.stream.Collectors.toList;
import com.ss.rlib.util.ClassUtils;
import com.ss.simple.network.log.parser.service.Service;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * The manager to manage all services.
 *
 * @author JavaSaBr
 */
public class ServiceManager {

    @NotNull
    private static final ServiceManager INSTANCE = new ServiceManager();

    public static @NotNull ServiceManager getInstance() {
        return INSTANCE;
    }

    /**
     * The list of created services.
     */
    @NotNull
    private final List<Service> services;

    private ServiceManager() {
        final ClasspathManager classpathManager = ClasspathManager.getInstance();
        this.services = classpathManager.findImplementations(Service.class).stream()
                .map(ClassUtils::newInstance)
                .map(Service.class::cast)
                .collect(toList());
        this.services.forEach(service -> service.init(this));
    }

    /**
     * Get the service of the type.
     *
     * @param type the type of the service.
     * @return the implementation of the service.
     * @throws RuntimeException if we can't find any implementation of the service.
     */
    public <T extends Service> @NotNull T getService(@NotNull final Class<T> type) {
        return services.stream().filter(type::isInstance)
                .map(type::cast)
                .findAny().orElseThrow(() -> new RuntimeException("not found implementation of the service " + type));
    }
}
