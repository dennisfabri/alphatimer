package org.lisasp.alphatimer.livetiming;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.server.PWA;

import org.lisasp.alphatimer.livetiming.data.service.CurrentHeatService;
import org.lisasp.alphatimer.livetiming.data.service.HeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.vaadin.artur.helpers.LaunchUtil;
import com.vaadin.flow.theme.Theme;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
@Push
@Theme(value = "livetiming")
@PWA(name = "Livetiming", shortName = "Livetiming", offlineResources = {"images/logo.png"})
public class Application extends SpringBootServletInitializer implements AppShellConfigurator {

    public static void main(String[] args) {
        LaunchUtil.launchBrowserInDevelopmentMode(SpringApplication.run(Application.class, args));
    }

    private CurrentHeatService chs;

    @Bean
    public CurrentHeatService getCurrentHeatService(@Autowired HeatRepository repository) {
        if (chs == null) {
            chs = new CurrentHeatService(repository);
        }
        return chs;
    }

}
