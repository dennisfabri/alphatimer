package de.dennisfabri.alphatimer.server;

import de.dennisfabri.alphatimer.serial.configuration.SerialConfiguration;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@Setter
@Getter
public class ConfigurationValues {
    @Value("${alphatimer.serialPort}")
    private String serialPort;
    @Value("${alphatimer.storagePath}")
    private String storagePath;
    @Value("${alphatimer.serialConfiguration}")
    private String serialConfiguration;
    @Value("${alphatimer.competitionKey}")
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
}
