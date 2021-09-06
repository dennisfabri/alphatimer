package org.lisasp.alphatimer.server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lisasp.alphatimer.api.protocol.json.BytesInputEventModule;
import org.lisasp.alphatimer.jre.date.ActualDate;
import org.lisasp.alphatimer.jre.date.ActualDateTime;
import org.lisasp.alphatimer.jre.date.DateFacade;
import org.lisasp.alphatimer.jre.date.DateTimeFacade;
import org.lisasp.alphatimer.legacy.LegacyRepository;
import org.lisasp.alphatimer.legacy.LegacyService;
import org.lisasp.alphatimer.protocol.MessageConverter;
import org.lisasp.alphatimer.refinedmessages.DataHandlingMessageRefiner;
import org.lisasp.alphatimer.spring.jms.JsonMessageConverter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
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
    LegacyService legacyTimeStorage(LegacyRepository repository) {
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
    JsonMessageConverter jsonMessageConverter(CachingConnectionFactory connectionFactory) {
        JsonMessageConverter messageConverter = new JsonMessageConverter();
        messageConverter.registerModule(new BytesInputEventModule());
        return messageConverter;
    }

    @Bean
    JmsTemplate jmsTemplate(CachingConnectionFactory connectionFactory, JsonMessageConverter jsonMessageConverter) {
        JmsTemplate template = new JmsTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter);
        return template;
    }
}
