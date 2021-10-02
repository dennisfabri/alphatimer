package org.lisasp.alphatimer.livetiming;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lisasp.alphatimer.api.protocol.json.BytesInputEventModule;
import org.lisasp.alphatimer.heats.service.DataRepository;
import org.lisasp.alphatimer.heats.service.HeatService;
import org.lisasp.basics.jre.date.ActualDateTime;
import org.lisasp.basics.jre.date.DateTimeFacade;
import org.lisasp.basics.spring.jms.JsonMessageConverter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.vaadin.artur.helpers.LaunchUtil;

import javax.jms.ConnectionFactory;

/**
 * The entry point of the Spring Boot application.
 * <p>
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 */
@Slf4j
@RequiredArgsConstructor
@ComponentScan("org.lisasp.alphatimer")
@EntityScan(basePackages = {"org.lisasp.alphatimer"})
@EnableJpaRepositories("org.lisasp.alphatimer")
@SpringBootApplication(scanBasePackages = {"org.lisasp.alphatimer"})
@Push
@Theme(value = "livetiming")
@PWA(name = "LiSaSp Livetiming", shortName = "Livetiming", offlineResources = {"images/logo.png"})
public class LivetimingServer extends SpringBootServletInitializer implements AppShellConfigurator {

    public static void main(String[] args) {
        LaunchUtil.launchBrowserInDevelopmentMode(SpringApplication.run(LivetimingServer.class, args));
    }

    @Bean
    DateTimeFacade dateTimeFacade() {
        return new ActualDateTime();
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    HeatService currentHeatService(DataRepository repository, DateTimeFacade dateTimeFacade) {
        return new HeatService(repository, dateTimeFacade);
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    JsonMessageConverter jsonMessageConverter(CachingConnectionFactory connectionFactory) {
        JsonMessageConverter messageConverter = new JsonMessageConverter();
        messageConverter.registerModule(new BytesInputEventModule());
        return messageConverter;
    }

    @Bean
    JmsTemplate jmsTemplate(CachingConnectionFactory connectionFactory, JsonMessageConverter messageConverter) {
        JmsTemplate template = new JmsTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }

    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerFactory(ConnectionFactory connectionFactory,
                                                                      DefaultJmsListenerContainerFactoryConfigurer configurer,
                                                                      JsonMessageConverter jsonMessageConverter){
        var factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setMessageConverter(jsonMessageConverter);
        return factory;
    }
}
