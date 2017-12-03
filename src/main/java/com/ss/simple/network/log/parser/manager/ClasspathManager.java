package com.ss.simple.network.log.parser.manager;

import com.ss.rlib.classpath.ClassPathScanner;
import com.ss.rlib.classpath.ClassPathScannerFactory;
import com.ss.rlib.util.array.Array;
import com.ss.rlib.util.array.ArrayFactory;
import org.jetbrains.annotations.NotNull;

/**
 * The manager to work with available classes in the classpath.
 *
 * @author JavaSaBr
 */
public class ClasspathManager {

    @NotNull
    private static final ClasspathManager INSTANCE = new ClasspathManager();

    public static @NotNull ClasspathManager getInstance() {
        return INSTANCE;
    }

    /**
     * The classpath scanner.
     */
    @NotNull
    private final ClassPathScanner scanner;

    private ClasspathManager() {
        this.scanner = ClassPathScannerFactory.newDefaultScanner();
        this.scanner.setUseSystemClasspath(true);
        this.scanner.scan(path -> !path.contains("jre"));
    }

    /**
     * Find available implementation og the interface in this classpath.
     *
     * @param type the type of an interface.
     * @return the list of available implementations.
     */
    public <T> @NotNull Array<Class<T>> findImplementations(@NotNull final Class<T> type) {
        final Array<Class<T>> result = ArrayFactory.newArray(Class.class);
        scanner.findImplements(result, type);
        return result;
    }
}
