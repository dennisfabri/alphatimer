package org.lisasp.alphatimer.server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lisasp.alphatimer.api.serial.SerialPortReader;
import org.lisasp.alphatimer.api.serial.Storage;
import org.lisasp.alphatimer.ares.serial.InputCollector;
import org.lisasp.alphatimer.legacy.LegacyService;
import org.lisasp.alphatimer.ares.serial.MessageConverter;
import org.lisasp.alphatimer.refinedmessages.DataHandlingMessageRefiner;
import org.lisasp.alphatimer.serial.tcp.TcpReader;
import org.lisasp.basics.jre.date.ActualDate;
import org.lisasp.basics.jre.date.ActualDateTime;
import org.lisasp.basics.jre.date.DateFacade;
import org.lisasp.basics.jre.date.DateTimeFacade;
import org.lisasp.basics.jre.io.ActualFile;
import org.lisasp.basics.jre.io.FileFacade;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.File;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@ComponentScan("org.lisasp.alphatimer")
@EntityScan(basePackages = {"org.lisasp.alphatimer"})
@EnableJpaRepositories("org.lisasp.alphatimer")
@SpringBootApplication(scanBasePackages = {"org.lisasp.alphatimer"})
public class AlphatimerServer {

    private final ConfigurableApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(AlphatimerServer.class, args);
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    LegacyService legacyTimeStorage(LegacyJPARepository repository) {
        return new LegacyService(repository);
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    MessageConverter messageConverter() {
        return new MessageConverter();
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    DataHandlingMessageRefiner messageRefiner() {
        return new DataHandlingMessageRefiner();
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    DateFacade dateFacade() {
        return new ActualDate();
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    DateTimeFacade dateTimeFacade() {
        return new ActualDateTime();
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    SerialPortReader serialPortReader(ConfigurationValues config) {
        return new TcpReader(config.getTcpServer(), config.getTcpPort());
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    Storage storage(ConfigurationValues config, FileFacade fileFacade, DateFacade dateFacade) throws IOException {
        log.info("Using data path: {}", new File(config.getStoragePath()).getCanonicalPath());
        return new Storage(config.getStoragePath(), fileFacade, dateFacade);
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    InputCollector inputCollector(ConfigurationValues config, DateTimeFacade dateTimeFacade) {
        return new InputCollector(config.getCompetitionKey(), dateTimeFacade);
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    FileFacade fileFacade() {
        return new ActualFile();
    }
}
