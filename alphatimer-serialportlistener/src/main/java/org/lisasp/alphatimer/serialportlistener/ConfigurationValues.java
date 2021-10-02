package org.lisasp.alphatimer.serialportlistener;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.lisasp.basics.jre.date.DateFacade;
import org.lisasp.alphatimer.serial.configuration.SerialConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
@Data
@RequiredArgsConstructor
public class ConfigurationValues {

    private final DateFacade dates;

    @Value("${alphatimer.serialPort:}")
    private String serialPort;
    @Value("${alphatimer.storagePath:data}")
    private String storagePath;
    @Value("${alphatimer.serialConfiguration:ARES21}")
    private String serialConfiguration;
    @Value("${alphatimer.competitionKey:}")
    private String competitionKey;

    SerialConfiguration getSerialConfigurationObject() {
        return getSerialConfigurationObject(getSerialConfiguration());
    }

    SerialConfiguration getSerialConfigurationObject(String serialConfiguration) {
        switch (serialConfiguration.toLowerCase(Locale.ROOT)) {
            case "quantum":
                return SerialConfiguration.Quantum;
            case "test":
                return SerialConfiguration.TEST;
            default:
                return SerialConfiguration.ARES21;
        }
    }

    String getActualCompetitionKey() {
        String currentCompetitionKey = getCompetitionKey();
        if (currentCompetitionKey == null || currentCompetitionKey.trim().isBlank()) {
            currentCompetitionKey = dates.today().format(DateTimeFormatter.ISO_LOCAL_DATE);
        }
        return currentCompetitionKey;
    }
}
