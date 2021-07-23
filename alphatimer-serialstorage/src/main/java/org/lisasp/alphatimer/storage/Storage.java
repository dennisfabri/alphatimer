package org.lisasp.alphatimer.storage;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.lisasp.alphatimer.jre.date.DateFacade;
import org.lisasp.alphatimer.jre.io.FileFacade;

import java.io.IOException;
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
        return fileFacade.read(getFilename());
    }

    public void write(byte b) throws IOException {
        fileFacade.write(getFilename(), b);
    }

    private String getFilename() {
        StringBuilder sb = new StringBuilder();
        sb.append(baseDir);
        if (!baseDir.endsWith(fileFacade.getSeparator())) {
            sb.append(fileFacade.getSeparator());
        }
        sb.append(dateTimeFacade.today().format(DateTimeFormatter.ISO_LOCAL_DATE));
        sb.append(".serial");
        return sb.toString();
    }
}
