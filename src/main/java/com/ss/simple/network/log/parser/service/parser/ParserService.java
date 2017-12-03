package com.ss.simple.network.log.parser.service.parser;

import com.ss.simple.network.log.parser.service.Service;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.util.Set;

/**
 * @author JavaSaBr
 */
public interface ParserService extends Service {

    @NotNull Set<String> getAvailableFormats();

    void parse(@NotNull InputStream in, @NotNull String format);
}
