package com.ss.simple.network.log.parser.test;

import com.ss.simple.network.log.parser.manager.ServiceManager;
import com.ss.simple.network.log.parser.service.StatisticsService;
import com.ss.simple.network.log.parser.service.parser.ParserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ParserServiceTests {

    @BeforeAll
    public static void setUp() {
        final ServiceManager serviceManager = ServiceManager.getInstance();
        final StatisticsService statisticsService = serviceManager.getService(StatisticsService.class);
        statisticsService.clear();
    }

    @Test
    public void testValidLog() throws IOException {

        final ServiceManager serviceManager = ServiceManager.getInstance();
        final ParserService parserService = serviceManager.getService(ParserService.class);
        final StatisticsService statisticsService = serviceManager.getService(StatisticsService.class);

        try (final InputStream inputStream = getClass().getResourceAsStream("/logs/valid.log")) {
            parserService.parse(inputStream, "CSV");
        }

        final Map<String, Long> expectedCounts = new HashMap<>();
        expectedCounts.put("www.google-analytics.com", 24L);
        expectedCounts.put("wl.dlservice.microsoft.com", 12L);
        expectedCounts.put("au.download.windowsupdate.com", 5L);
        expectedCounts.put("i4.ytimg.com", 5L);
        expectedCounts.put("fs2.migahost.com", 3L);
        expectedCounts.put("img.imgsmail.ru", 3L);

        statisticsService.handleHostCounts((host, count) -> {
            final Long expected = expectedCounts.get(host);
            if (expected != null) {
                expectedCounts.remove(host);
                Assertions.assertEquals(expected, count, "Unexpected count for the host " + host);
            }
        });

        Assertions.assertEquals(0, expectedCounts.size(), "Not all hosts weren't parsed.");
    }

    @Test
    public void testNotValidLog() throws IOException {

        final ServiceManager serviceManager = ServiceManager.getInstance();
        final ParserService parserService = serviceManager.getService(ParserService.class);

        try (final InputStream inputStream = getClass().getResourceAsStream("/logs/notvalid.log")) {
            parserService.parse(inputStream, "CSV");
        }
    }

    @Test
    public void testOneLineLog() throws IOException {

        final ServiceManager serviceManager = ServiceManager.getInstance();
        final ParserService parserService = serviceManager.getService(ParserService.class);

        try (final InputStream inputStream = getClass().getResourceAsStream("/logs/oneline.log")) {
            parserService.parse(inputStream, "CSV");
        }
    }
}
