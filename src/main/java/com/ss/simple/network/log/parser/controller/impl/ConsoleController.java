package com.ss.simple.network.log.parser.controller.impl;

import static java.util.stream.Collectors.joining;
import com.ss.rlib.logging.Logger;
import com.ss.rlib.logging.LoggerManager;
import com.ss.rlib.util.ArrayUtils;
import com.ss.rlib.util.StringUtils;
import com.ss.rlib.util.array.ArrayFactory;
import com.ss.simple.network.log.parser.controller.Controller;
import com.ss.simple.network.log.parser.manager.ServiceManager;
import com.ss.simple.network.log.parser.service.StatisticsService;
import com.ss.simple.network.log.parser.service.parser.ParserService;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Predicate;

/**
 * The controller to provide console API to work with this parser.
 *
 * @author JavaSaBr
 */
public class ConsoleController implements Controller {

    @NotNull
    private static final Logger LOGGER = LoggerManager.getLogger(ConsoleController.class);

    @NotNull
    private static final int[] AVAILABLE_OPTIONS = ArrayFactory.toIntegerArray(1, 2);

    /**
     * The parser service.
     */
    @NotNull
    private final ParserService parserService;

    /**
     * The statistics service.
     */
    @NotNull
    private final StatisticsService statisticsService;

    /**
     * The working thread.
     */
    @NotNull
    private final Thread thread;

    /**
     * The available formats to parse.
     */
    @NotNull
    private final Set<String> availableFormats;

    /**
     * The printed version of available formats.
     */
    @NotNull
    private final String formatsString;

    public ConsoleController() {
        this.thread = new Thread(this::runInThread);
        this.thread.setName(getClass().getSimpleName());
        final ServiceManager serviceManager = ServiceManager.getInstance();
        this.parserService = serviceManager.getService(ParserService.class);
        this.statisticsService = serviceManager.getService(StatisticsService.class);
        this.availableFormats = new HashSet<>(parserService.getAvailableFormats());
        this.formatsString = parserService.getAvailableFormats().stream()
                .collect(joining(","));
    }

    /**
     * The loop of console interface.
     */
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

                    final Path file = readFile(scanner);

                    System.out.print("Format[" + formatsString + "]:");

                    final String format = readFormat(scanner);

                    try (final InputStream in = Files.newInputStream(file)) {
                        parserService.parse(in, format);
                    } catch (final IOException e) {
                        LOGGER.error(e);
                    }

                } else if (option == 2) {
                    statisticsService.handleHostCounts((host, count) ->
                            System.out.println("Host: " + host + ", Count: " + count));
                }
            }
        }
    }

    /**
     * Read a format of parsed file.
     *
     * @param scanner the scanner.
     * @return the valid format.
     */
    private @NotNull String readFormat(@NotNull final Scanner scanner) {
        do {

            final String inputFormat = scanner.next()
                    .trim().toUpperCase();

            LOGGER.debug(inputFormat, val -> "input format: " + val);

            if (!availableFormats.contains(inputFormat)) {
                System.out.print("Incorrect format, try again:");
                continue;
            }

            return inputFormat;

        } while (true);
    }

    /**
     * Read a file to parse.
     *
     * @param scanner the scanner.
     * @return the valid file.
     */
    private @NotNull Path readFile(@NotNull final Scanner scanner) {
        Path file;
        do {

            final String inputPath = nextLineUntil(scanner, line -> !StringUtils.isEmpty(line));

            // /home/javasabr/Загрузки/log_example.log
            LOGGER.debug(inputPath, val -> "input path: " + val);

            try {
                file = Paths.get(inputPath);
            } catch (final InvalidPathException e) {
                System.out.print("Incorrect path, try again:");
                continue;
            }

            if (!Files.exists(file)) {
                System.out.print("Not found the file, try again:");
                continue;
            }

            return file;

        } while (true);
    }

    /**
     * Read an option.
     *
     * @param scanner the scanner.
     * @return the valid option.
     */
    private int readOption(@NotNull final Scanner scanner) {
        int option;
        do {

            final String readValue = scanner.next();

            LOGGER.debug(readValue, val -> "read value: " + val);
            try {
                option = Integer.parseInt(readValue);
            } catch (final NumberFormatException e) {
                System.out.print("Incorrect option, try again:");
                continue;
            }

            if (!ArrayUtils.contains(AVAILABLE_OPTIONS, option)) {
                System.out.print("Incorrect option, try again:");
                continue;
            }

            return option;

        } while (true);
    }

    /**
     * Read next lines until when the condition will be completed.
     *
     * @param scanner   the scanner.
     * @param condition the condition.
     * @return the requested line.
     */
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
