package org.lisasp.alphatimer.test.heats;

import lombok.RequiredArgsConstructor;
import org.lisasp.alphatimer.heats.service.DataRepository;
import org.lisasp.alphatimer.heats.service.HeatService;
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
    HeatService currentHeatService(DataRepository repository, DateTimeFacade dateTimeFacade) {
        return new HeatService(repository, dateTimeFacade);
    }
}
