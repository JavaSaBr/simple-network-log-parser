package com.ss.simple.network.log.parser.controller.impl;

import com.ss.rlib.logging.Logger;
import com.ss.rlib.logging.LoggerManager;
import com.ss.rlib.util.ArrayUtils;
import com.ss.rlib.util.StringUtils;
import com.ss.rlib.util.array.ArrayFactory;
import com.ss.simple.network.log.parser.controller.Controller;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Predicate;

/**
 * @author JavaSaBr
 */
public class ConsoleController implements Controller {

    @NotNull
    private static final Logger LOGGER = LoggerManager.getLogger(ConsoleController.class);

    @NotNull
    private static final int[] AVAILABLE_FIRST_OPTIONS = ArrayFactory.toIntegerArray(1, 2);

    @NotNull
    private final Thread thread;

    @NotNull
    private final Set<String> availableFormats;

    public ConsoleController() {
        this.thread = new Thread(this::runInThread);
        this.thread.setName(getClass().getSimpleName());
        this.availableFormats = new HashSet<>();
        this.availableFormats.add("csv");
    }

    private void runInThread() {
        LOGGER.info("Started.");

        try (final Scanner scanner = new Scanner(System.in)) {
            while (true) {

                System.out.println("1. Read a new log file.");
                System.out.println("2. Show the current result.");
                System.out.print("Choose an option:");

                final int option = readOption(scanner);

                LOGGER.debug(option, val -> "chosen option: " + val);

                if (option == 1) {

                    System.out.print("Path to the file:");

                    Path file;
                    do {

                        final String inputPath = nextLineUntil(scanner, line -> !StringUtils.isEmpty(line));

                        // /home/javasabr/Загрузки/log_example.log
                        LOGGER.debug(inputPath, val -> "input path: " + val);

                        final Path path;
                        try {
                            path = Paths.get(inputPath);
                        } catch (final InvalidPathException e) {
                            System.out.print("You have input an incorrect path, try again:");
                            continue;
                        }

                        if (!Files.exists(path)) {
                            System.out.print("Not found the file, try again:");
                            continue;
                        }

                        file = path;
                        break;

                    } while (true);

                    System.out.print("Format[csv]:");

                    final String inputFormat = scanner.next();

                    LOGGER.debug(inputFormat, val -> "input format: " + val);
                }
            }
        }
    }

    private int readOption(@NotNull final Scanner scanner) {
        int option;
        do {

            final String readValue = scanner.next();

            LOGGER.debug(readValue, val -> "read value: " + val);
            try {
                option = Integer.parseInt(readValue);
            } catch (final NumberFormatException e) {
                System.out.print("You have input an incorrect option, try again:");
                continue;
            }

            if (!ArrayUtils.contains(AVAILABLE_FIRST_OPTIONS, option)) {
                System.out.print("You have input an incorrect option, try again:");
                continue;
            }

            return option;

        } while (true);
    }

    private static @NotNull String nextLineUntil(@NotNull final Scanner scanner,
                                                 @NotNull final Predicate<@NotNull String> condition) {
        while (scanner.hasNextLine()) {
            final String line = scanner.nextLine();
            if (condition.test(line)) return line;
        }

        throw new IllegalArgumentException("not found any matched line.");
    }

    @Override
    public void start() {
        thread.start();
    }

    @Override
    public void stop() {
        thread.interrupt();
    }
}
