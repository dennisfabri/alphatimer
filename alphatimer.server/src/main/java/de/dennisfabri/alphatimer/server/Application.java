package de.dennisfabri.alphatimer.server;

import de.dennisfabri.alphatimer.collector.InputCollector;
import de.dennisfabri.alphatimer.legacy.LegacyTimeStorage;
import de.dennisfabri.alphatimer.serial.DefaultSerialConnectionBuilder;
import de.dennisfabri.alphatimer.serial.SerialConnectionBuilder;
import de.dennisfabri.alphatimer.storage.ActualDate;
import de.dennisfabri.alphatimer.storage.ActualFile;
import de.dennisfabri.alphatimer.storage.DateFacade;
import de.dennisfabri.alphatimer.storage.Storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@Configuration
@ComponentScan("de.dennisfabri.alphatimer")
@EntityScan(basePackages = {"de.dennisfabri.alphatimer"})
@EnableJpaRepositories("de.dennisfabri.alphatimer")
@SpringBootApplication(scanBasePackages = {"de.dennisfabri.alphatimer"})
public class Application implements ApplicationRunner {

    @Autowired
    private ConfigurableApplicationContext context;

    public static void main(String[] args) throws Exception {
        if (new CommandLineInterpreter().run(args)) {
            return;
        }

        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
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

        context.getBean(SerialInterpreter.class).start();
    }

    @Bean
    SerialConnectionBuilder getSerialConnectionBuilder() {
        return new DefaultSerialConnectionBuilder();
    }

    @Bean
    Storage createStorage(ConfigurationValues config) throws IOException {
        log.info("Using data path   : {}", new File(config.getStoragePath()).getCanonicalPath());
        return new Storage(config.getStoragePath(), new ActualFile(), new ActualDate());
    }

    @Bean
    LegacyTimeStorage createLegacyTimeStorage() {
        return new LegacyTimeStorage();
    }

    @Bean
    InputCollector createAlphaTranslator() {
        return new InputCollector();
    }

    @Bean
    DateFacade createDateFacade() {
        return new ActualDate();
    }
}
