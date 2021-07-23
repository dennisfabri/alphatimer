package org.lisasp.alphatimer.heats;

import lombok.RequiredArgsConstructor;
import org.lisasp.alphatimer.heats.current.service.CurrentDataRepository;
import org.lisasp.alphatimer.heats.current.service.CurrentHeatService;
import org.lisasp.alphatimer.jre.date.ActualDateTime;
import org.lisasp.alphatimer.jre.date.DateTimeFacade;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@RequiredArgsConstructor
@ComponentScan("org.lisasp.alphatimer")
@EntityScan(basePackages = {"org.lisasp.alphatimer"})
@EnableJpaRepositories("org.lisasp.alphatimer")
@SpringBootApplication(scanBasePackages = {"org.lisasp.alphatimer"})
public class TestApplication {
    @Bean
    DateTimeFacade dateTimeFacade() {
        return new ActualDateTime();
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    CurrentHeatService currentHeatService(CurrentDataRepository repository, DateTimeFacade dateTimeFacade) {
        return new CurrentHeatService(repository, dateTimeFacade);
    }
}
