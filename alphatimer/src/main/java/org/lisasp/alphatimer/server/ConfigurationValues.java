package org.lisasp.alphatimer.server;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.lisasp.basics.jre.date.DateFacade;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
@RequiredArgsConstructor
public class ConfigurationValues {

    private final DateFacade dates;

    @Value("${alphatimer.serial.tcp.server}")
    private String tcpServer;
    @Value("${alphatimer.serial.tcp.port:8585}")
    private int tcpPort;

    @Value("${alphatimer.storagePath:data}")
    private String storagePath;
    @Value("${alphatimer.competitionKey}")
    private String competitionKey;

    @Value("${alphatimer.queues.refined:refined-messages}")
    private String refinedQueueName;
}
