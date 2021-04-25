package de.dennisfabri.alphatimer.storage;

import lombok.NonNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ActualFile implements FileFacade {
    @Override
    public void write(@NonNull String filename, byte b) throws IOException {
        Files.createDirectories(Path.of(filename).getParent());
        Files.write(Path.of(filename),
                    new byte[]{b},
                    StandardOpenOption.WRITE,
                    StandardOpenOption.APPEND,
                    StandardOpenOption.CREATE);
    }

    @Override
    public byte[] read(@NonNull String filename) throws IOException {
        try {
            return Files.readAllBytes(Path.of(filename));
        } catch (NoSuchFileException ex) {
            // if the file is not found, nothing has been saved yet.
            return new byte[0];
        }
    }

    @Override
    public String getSeparator() {
        return File.separator;
    }
}
