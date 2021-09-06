package org.lisasp.alphatimer.serialportlistener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lisasp.alphatimer.api.protocol.json.BytesInputEventModule;
import org.lisasp.alphatimer.jre.date.ActualDate;
import org.lisasp.alphatimer.jre.date.ActualDateTime;
import org.lisasp.alphatimer.jre.date.DateFacade;
import org.lisasp.alphatimer.jre.date.DateTimeFacade;
import org.lisasp.alphatimer.jre.io.ActualFile;
import org.lisasp.alphatimer.protocol.InputCollector;
import org.lisasp.alphatimer.serial.DefaultSerialConnectionBuilder;
import org.lisasp.alphatimer.serial.SerialConnectionBuilder;
import org.lisasp.alphatimer.serial.Storage;
import org.lisasp.alphatimer.spring.jms.JsonMessageConverter;
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
    InputCollector inputCollector(ConfigurationValues config, DateTimeFacade dateTimeFacade) {
        return new InputCollector(config.getCompetitionKey(), dateTimeFacade);
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
    JsonMessageConverter jsonMessageConverter(CachingConnectionFactory connectionFactory) {
        JsonMessageConverter messageConverter = new JsonMessageConverter();
        messageConverter.registerModule(new BytesInputEventModule());
        return messageConverter;
    }

    /*
    @Bean
    JmsTemplate jmsTemplate(CachingConnectionFactory connectionFactory, JsonMessageConverter jsonMessageConverter) {
        connectionFactory.setExceptionListener(e -> log.error("JMS-Exception", e));

        JmsTemplate template = new JmsTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter);
        template.setDefaultDestinationName("ares-messages-local");
        return template;
    }
    */
}
