package org.lisasp.alphatimer.serialportlistener;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lisasp.alphatimer.api.ares.serial.json.BytesInputEventModule;
import org.lisasp.alphatimer.serial.*;
import org.lisasp.alphatimer.serial.exceptions.NoPortsFoundException;
import org.lisasp.basics.jre.date.ActualDate;
import org.lisasp.basics.jre.date.ActualDateTime;
import org.lisasp.basics.jre.date.DateFacade;
import org.lisasp.basics.jre.date.DateTimeFacade;
import org.lisasp.basics.jre.io.ActualFile;
import org.lisasp.alphatimer.ares.serial.InputCollector;
import org.lisasp.basics.spring.jms.JsonMessageConverter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

import java.io.File;
import java.io.IOException;
import java.io.Serial;

@Slf4j
@RequiredArgsConstructor
@ComponentScan("org.lisasp.alphatimer")
@SpringBootApplication(scanBasePackages = {"org.lisasp.alphatimer"})
public class SerialPortListenerApplication implements ApplicationRunner {

    private final ConfigurableApplicationContext context;

    public static void main(String[] args) throws Exception {
        if (new CommandLineInterpreter().run(args)) {
            return;
        }

        SpringApplication.run(SerialPortListenerApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        configureJmsMessageConverter();
        startSerialPortListener();
    }

    private void startSerialPortListener() {
        context.getBean(SerialInterpreter.class).start();
    }

    private void configureJmsMessageConverter() {
        JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
        jmsTemplate.setMessageConverter(context.getBean(JsonMessageConverter.class));
    }

    @Bean
    SerialConnectionBuilder serialConnectionBuilder() {
        return new DefaultSerialConnectionBuilder();
    }

    @Bean
    SerialPortReader serialPortReader(SerialConnectionBuilder serialConnectionBuilder, ConfigurationValues config) throws IOException {
        switch (config.getMode()) {
            case Serial:
                return createHardwarePortReader(serialConnectionBuilder, config);
            case Tcp:
                return createNetworkPortReader(config);
        }
        throw new IllegalStateException("This code must not be reached.");
    }

    private SerialPortReader createNetworkPortReader(ConfigurationValues config) throws IOException {
        return new TcpReader(config.getTcpServer(), config.getTcpPort());
    }

    private SerialPortReader createHardwarePortReader(SerialConnectionBuilder serialConnectionBuilder, ConfigurationValues config) throws IOException {
        try {
            String port = config.getSerialPort();
            if (hasNoValue(port)) {
                port = serialConnectionBuilder.autoconfigurePort();
            }

            log.info("Connecting to port: {}", port);
            return serialConnectionBuilder.configure(port, config.getSerialConfigurationObject()).buildReader();
        } catch (NoPortsFoundException | NoSuchPortException nsp) {
            log.error("No port with specified name present.");
            throw new IOException("No port with specified name present.", nsp);
        } catch (PortInUseException nsp) {
            log.error("Specified port is already in use.");
            throw new IOException("Specified port is already in use.", nsp);
        } catch (UnsupportedCommOperationException uco) {
            log.error("Unknown communication error occurred.", uco);
            throw new IOException("Unknown communication error occurred.", uco);
        }
    }

    private boolean hasNoValue(String port) {
        return port == null || port.trim().equals("");
    }

    @Bean
    Storage storage(ConfigurationValues config) throws IOException {
        log.info("Using data path   : {}", new File(config.getStoragePath()).getCanonicalPath());
        return new Storage(config.getStoragePath(), new ActualFile(), new ActualDate());
    }

    @Bean
    InputCollector inputCollector(ConfigurationValues config, DateTimeFacade dateTimeFacade) {
        return new InputCollector(config.getCompetitionKey(), dateTimeFacade);
    }

    @Bean
    DateFacade dateFacade() {
        return new ActualDate();
    }

    @Bean
    DateTimeFacade dateTimeFacade() {
        return new ActualDateTime();
    }

    @Bean
    JsonMessageConverter jsonMessageConverter(CachingConnectionFactory connectionFactory) {
        JsonMessageConverter messageConverter = new JsonMessageConverter();
        messageConverter.registerModule(new BytesInputEventModule());
        return messageConverter;
    }
}
