package org.lisasp.alphatimer.server;

import org.lisasp.alphatimer.serial.configuration.SerialConfiguration;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@Data
public class ConfigurationValues {
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
}
