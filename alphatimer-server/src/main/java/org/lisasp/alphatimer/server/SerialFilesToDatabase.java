package org.lisasp.alphatimer.server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lisasp.alphatimer.api.protocol.DataHandlingMessageAggregator;
import org.lisasp.alphatimer.api.protocol.DataHandlingMessageRepository;
import org.lisasp.alphatimer.messagesstorage.AresMessageRepository;
import org.lisasp.alphatimer.messagesstorage.Messages;
import org.lisasp.alphatimer.protocol.InputCollector;
import org.lisasp.alphatimer.protocol.MessageAggregator;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Locale;

@Slf4j
@RequiredArgsConstructor
@Component
class SerialFilesToDatabase {

    private final AresMessageRepository repository;

    void transfer(String path) throws IOException {
        Arrays.stream(getFilenamesIn(path, ".serial")).forEach(file -> transferFile(path, file));
    }

    private String[] getFilenamesIn(String path, String suffix) throws IOException {
        return Files.list(Path.of(path))
                    .map(p -> p.getFileName().toString())
                    .filter(p -> p.toLowerCase(Locale.ROOT).endsWith(".serial"))
                    .map(p -> p.substring(0, p.lastIndexOf("."))).toArray(String[]::new);
    }

    private void transferFile(String path, String file) {
        log.info("Transferring file {}", file);

        try (final InputCollector alphaTranslator = new InputCollector()) {
            DataHandlingMessageRepository messages = new Messages(repository);

            DataHandlingMessageAggregator aggregator = new MessageAggregator(event -> messages.put(event, file));
            alphaTranslator.register(event -> {
                log.debug("Received message: '{}'", event);
                aggregator.accept(event);
            });

            for (byte b : Files.readAllBytes(getPath(path, file))) {
                alphaTranslator.accept(b);
            }
        } catch (Exception ex) {
            log.error("Transfer-Error", ex);
        }
    }

    private Path getPath(String path, String file) {
        return Path.of(String.format("%s/%s.serial",
                                     path.endsWith(File.separator) ?
                                     path.substring(0, path.length() - File.separator.length()) : path,
                                     file));
    }
}
