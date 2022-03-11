package org.lisasp.alphatimer.serialportlistener;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
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

    @Value("${alphatimer.connection.mode:tcp}")
    private String connectionMode;
    @Value("${alphatimer.connection.tcp.server:dlrg-areserial}")
    private String tcpServer;
    @Value("${alphatimer.connection.tcp.port:8585}")
    private int tcpPort;
    @Value("${alphatimer.connection.serial.port:}")
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

    ConnectionMode getMode() {
        if (Strings.isBlank(connectionMode)) {
            return ConnectionMode.Serial;
        }
        switch (connectionMode.trim().toLowerCase()) {
            case "tcp":
                return ConnectionMode.Tcp;
            case "serial":
                return ConnectionMode.Serial;
            default:
                throw new IllegalArgumentException(String.format("Mode '%s' is not 'serial' or 'tcp'.", connectionMode));
        }
    }
}
