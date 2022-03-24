package org.lisasp.alphatimer.serialportlistener.tcp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.IOException;

@Configuration
public class TcpConfig {

    @Value("${alphatimer.tcp.server.port:8585}")
    private int port;

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public TcpServer tcpServer() throws IOException {
        return new TcpServer(port);
    }
}
