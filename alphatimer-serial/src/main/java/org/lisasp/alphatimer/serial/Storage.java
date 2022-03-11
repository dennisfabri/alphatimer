package org.lisasp.alphatimer.serial;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.lisasp.basics.jre.date.DateFacade;
import org.lisasp.basics.jre.io.FileFacade;

import java.io.IOException;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
public class Storage {
    @NonNull
    private final String baseDir;
    @NonNull
    private final FileFacade fileFacade;
    @NonNull
    private final DateFacade dateTimeFacade;

    public byte[] read() throws IOException {
        return fileFacade.get(getFilename());
    }

    public void write(byte b) throws IOException {
        fileFacade.append(getFilename(), b);
    }

    private Path getFilename() {
        return Path.of(baseDir, String.format("%s.serial", dateTimeFacade.today().format(DateTimeFormatter.ISO_LOCAL_DATE)));
    }
}
