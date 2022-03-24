package org.lisasp.alphatimer.serialportlistener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lisasp.alphatimer.api.serial.SerialConnectionBuilder;
import org.lisasp.alphatimer.api.serial.SerialPortReader;
import org.lisasp.alphatimer.api.serial.exceptions.NoPortsFoundException;
import org.lisasp.alphatimer.ares.serial.InputCollector;
import org.lisasp.alphatimer.serial.com.DefaultSerialConnectionBuilder;
import org.lisasp.alphatimer.api.serial.Storage;
import org.lisasp.basics.jre.date.ActualDate;
import org.lisasp.basics.jre.date.ActualDateTime;
import org.lisasp.basics.jre.date.DateFacade;
import org.lisasp.basics.jre.date.DateTimeFacade;
import org.lisasp.basics.jre.io.ActualFile;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.io.File;
import java.io.IOException;

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
        startSerialPortListener();
    }

    private void startSerialPortListener() {
        context.getBean(SerialInterpreter.class).start();
    }

    @Bean
    SerialConnectionBuilder serialConnectionBuilder() {
        return new DefaultSerialConnectionBuilder();
    }

    @Bean
    SerialPortReader serialPortReader(SerialConnectionBuilder serialConnectionBuilder, ConfigurationValues config) throws IOException {
        try {
            String port = config.getSerialPort();
            if (hasNoValue(port)) {
                port = serialConnectionBuilder.autoconfigurePort();
            }

            log.info("Connecting to port: {}", port);
            return serialConnectionBuilder.configure(port, config.getSerialConfigurationObject()).buildReader();
        } catch (NoPortsFoundException uco) {
            log.error("No ports found.", uco);
            throw new IOException("No ports found.", uco);
        }
    }

    private boolean hasNoValue(String port) {
        return port == null || port.trim().equals("");
    }

    @Bean
    Storage storage(ConfigurationValues config) throws IOException {
        log.info("Using data path: {}", new File(config.getStoragePath()).getCanonicalPath());
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
}
