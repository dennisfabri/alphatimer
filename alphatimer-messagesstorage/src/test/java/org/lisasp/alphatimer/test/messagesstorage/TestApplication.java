package org.lisasp.alphatimer.test.messagesstorage;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan("org.lisasp.alphatimer.messagesstorage")
@EntityScan(basePackages = {"org.lisasp.alphatimer.messagesstorage"})
@EnableJpaRepositories("org.lisasp.alphatimer.messagesstorage")
@SpringBootApplication(scanBasePackages = {"org.lisasp.alphatimer.messagesstorage"})
public class TestApplication {
}
