package org.lisasp.alphatimer.livetiming.data.generator;

import com.vaadin.flow.spring.annotation.SpringComponent;

import org.lisasp.alphatimer.livetiming.data.service.HeatRepository;
import org.lisasp.alphatimer.livetiming.data.entity.Heat;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.vaadin.artur.exampledata.DataType;
import org.vaadin.artur.exampledata.ExampleDataGenerator;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(HeatRepository heatRepository) {
        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (heatRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }
            int seed = 123;

            logger.info("Generating demo data");

            logger.info("... generating 100 Heat entities...");
            ExampleDataGenerator<Heat> heatRepositoryGenerator = new ExampleDataGenerator<>(Heat.class,
                                                                                            LocalDateTime.of(2021, 6, 1, 0, 0, 0));
            heatRepositoryGenerator.setData(Heat::setId, DataType.ID);
            heatRepositoryGenerator.setData(Heat::setEvent, DataType.NUMBER_UP_TO_1000);
            heatRepositoryGenerator.setData(Heat::setHeat, DataType.NUMBER_UP_TO_100);
            // heatRepositoryGenerator.setData(Heat::setLanes, DataType.SENTENCE);
            heatRepository.saveAll(heatRepositoryGenerator.create(100, seed));

            logger.info("Generated demo data");
        };
    }
}
