package de.dennisfabri.alphatimer.server;

import de.dennisfabri.alphatimer.protocol.DataHandlingMessageAggregator;
import de.dennisfabri.alphatimer.protocol.InputCollector;
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
import java.util.Locale;

@Slf4j
@RequiredArgsConstructor
@Component
class SerialFilesToDatabase {

    private final AresMessageRepository repository;

    void transfer(String path) throws IOException {
        String[] files = Files.list(Path.of(path)).map(p -> p.getFileName().toString()).filter(p -> p.toLowerCase(Locale.ROOT).endsWith(".serial")).map(p -> p.substring(0, p.lastIndexOf("."))).toArray(String[]::new);
        Arrays.stream(files).forEach(file -> transferFile(path, file));
    }

    private void transferFile(String path, String file) {
        log.info("Transferring file {}", file);

        try (final InputCollector alphaTranslator = new InputCollector()) {
            Messages messages = new Messages(repository);

            DataHandlingMessageAggregator aggregator = new DataHandlingMessageAggregator(event -> messages.put(event, file));
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
