package org.lisasp.alphatimer.server;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.lisasp.alphatimer.jre.date.DateFacade;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
@Data
@RequiredArgsConstructor
public class ConfigurationValues {

    private final DateFacade dates;

    // @Value("${alphatimer.storagePath:data}")
    // private String storagePath;

    @Value("${alphatimer.queues.ares:ares-messages}")
    private String aresQueueName;
    @Value("${alphatimer.queues.refined:refined-messages}")
    private String refinedQueueName;
}
