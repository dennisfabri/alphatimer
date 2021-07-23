package org.lisasp.alphatimer.livetiming;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lisasp.alphatimer.heats.current.service.CurrentDataRepository;
import org.lisasp.alphatimer.heats.current.service.CurrentHeatService;
import org.lisasp.alphatimer.heats.current.service.CurrentHeatRepository;
import org.lisasp.alphatimer.heats.current.service.CurrentLaneRepository;
import org.lisasp.alphatimer.jre.date.ActualDateTime;
import org.lisasp.alphatimer.jre.date.DateTimeFacade;
import org.lisasp.alphatimer.spring.jms.JsonMessageConverter;
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
public class Application extends SpringBootServletInitializer implements AppShellConfigurator {

    public static void main(String[] args) {
        LaunchUtil.launchBrowserInDevelopmentMode(SpringApplication.run(Application.class, args));
    }

    @Bean
    DateTimeFacade dateTimeFacade() {
        return new ActualDateTime();
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    CurrentHeatService currentHeatService(CurrentDataRepository repository, DateTimeFacade dateTimeFacade) {
        return new CurrentHeatService(repository, dateTimeFacade);
    }

    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerFactory(ConnectionFactory connectionFactory,
                                                                            DefaultJmsListenerContainerFactoryConfigurer configurer) {
        var factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setMessageConverter(new JsonMessageConverter());
        return factory;
    }
}
