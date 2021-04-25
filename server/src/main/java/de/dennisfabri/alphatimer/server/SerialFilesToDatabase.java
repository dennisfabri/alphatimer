package de.dennisfabri.alphatimer.server;

import de.dennisfabri.alphatimer.api.protocol.events.messages.DataHandlingMessage;
import de.dennisfabri.alphatimer.collector.DataHandlingMessageAggregator;
import de.dennisfabri.alphatimer.collector.InputCollector;
import de.dennisfabri.alphatimer.messagesstorage.AresMessageRepository;
import de.dennisfabri.alphatimer.messagesstorage.Messages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
@Component
class SerialFilesToDatabase {

    private final AresMessageRepository repository;

    private static final String[] files = new String[]{"DM2008Freitag", "DM2008Samstag", "DM2009", "DM2010",
            "JRP2019Freitag", "JRP2019Samstag", "JRP2019Sonntag",
            "DP2019Freitag", "DP2019Samstag",
            "DMM2019Freitag", "DMM2019Samstag", "DMM2019Sonntag"};

    void transfer(String path) throws IOException {
        String[] filenames = Files.list(Path.of(path)).map(p -> p.getFileName().toString()).toArray(String[]::new);
        Arrays.stream(files).forEach(file -> transferFile(path, file));
    }

    private void transferFile(String path, String file) {
        log.info("Transferring file {}", file);

        try (final InputCollector alphaTranslator = new InputCollector()) {
            Messages messages = new Messages(repository);

            DataHandlingMessageAggregator aggregator = new DataHandlingMessageAggregator();
            aggregator.register(event -> {
                log.info("Received message: '{}'", event);
                if (event instanceof DataHandlingMessage) {
                    log.info("messages.put({})", event);
                    messages.put((DataHandlingMessage) event, file);
                }
            });
            alphaTranslator.register(event -> {
                log.info("Received message: '{}'", event);
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
