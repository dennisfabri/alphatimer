package org.lisasp.alphatimer.artemismq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class ArtemisMQServer {

    public static void main(String[] args) {
        SpringApplication.run(ArtemisMQServer.class, args);
    }

}
