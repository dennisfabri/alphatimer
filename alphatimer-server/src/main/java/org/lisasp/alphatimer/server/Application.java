package org.lisasp.alphatimer.server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lisasp.alphatimer.legacy.LegacyTimeStorage;
import org.lisasp.alphatimer.protocol.InputCollector;
import org.lisasp.alphatimer.refinedmessages.DataHandlingMessageRefiner;
import org.lisasp.alphatimer.serial.DefaultSerialConnectionBuilder;
import org.lisasp.alphatimer.serial.SerialConnectionBuilder;
import org.lisasp.alphatimer.spring.jms.JsonMessageConverter;
import org.lisasp.alphatimer.jre.date.ActualDate;
import org.lisasp.alphatimer.jre.io.ActualFile;
import org.lisasp.alphatimer.jre.date.DateFacade;
import org.lisasp.alphatimer.storage.Storage;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@ComponentScan("org.lisasp.alphatimer")
@EntityScan(basePackages = {"org.lisasp.alphatimer"})
@EnableJpaRepositories("org.lisasp.alphatimer")
@SpringBootApplication(scanBasePackages = {"org.lisasp.alphatimer"})
public class Application implements ApplicationRunner {

    private final ConfigurableApplicationContext context;

    public static void main(String[] args) throws Exception {
        if (new CommandLineInterpreter().run(args)) {
            return;
        }

        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (args.containsOption("SerialFilesToDatabase")) {
            List<String> values = args.getOptionValues("SerialFilesToDatabase");
            if (values.size() == 1) {
                context.getBean(SerialFilesToDatabase.class).transfer(values.get(0));
            }

            context.close();
            return;
        }

        context.getBean(SerialDataPreloader.class).preload();
        context.getBean(SerialInterpreter.class).start();
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    SerialConnectionBuilder serialConnectionBuilder() {
        return new DefaultSerialConnectionBuilder();
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    Storage storage(ConfigurationValues config) throws IOException {
        log.info("Using data path   : {}", new File(config.getStoragePath()).getCanonicalPath());
        return new Storage(config.getStoragePath(), new ActualFile(), new ActualDate());
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    LegacyTimeStorage legacyTimeStorage() {
        return new LegacyTimeStorage();
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    InputCollector inputCollector() {
        return new InputCollector();
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
    JmsTemplate jmsTemplate(CachingConnectionFactory connectionFactory) {
        JmsTemplate template = new JmsTemplate(connectionFactory);
        template.setMessageConverter(new JsonMessageConverter());
        return template;
    }
}
