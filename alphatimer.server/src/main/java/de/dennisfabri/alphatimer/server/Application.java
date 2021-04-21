package de.dennisfabri.alphatimer.server;

import de.dennisfabri.alphatimer.serial.DefaultSerialConnectionBuilder;
import de.dennisfabri.alphatimer.serial.SerialConnectionBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Locale;

@Configuration
@ComponentScan("de.dennisfabri.alphatimer")
@EntityScan(basePackages = {"de.dennisfabri.alphatimer"})
@EnableJpaRepositories("de.dennisfabri.alphatimer")
@SpringBootApplication(scanBasePackages = {"de.dennisfabri.alphatimer"})
public class Application {

    public static void main(String[] args) throws Exception {
        if (new CommandLineInterpreter().run(args)) {
            return;
        }

        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        context.getBean(SerialInterpreter.class).start();
    }

    @Bean
    SerialConnectionBuilder getSerialConnectionBuilder() {
        return new DefaultSerialConnectionBuilder();
    }
}
