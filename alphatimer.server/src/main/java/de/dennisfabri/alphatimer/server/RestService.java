package de.dennisfabri.alphatimer.server;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestService {

    private final SerialInterpreter interpreter;

    @GetMapping("/heats.xml")
    String getLegacyHeats() {
        return interpreter.getLegacyData();
    }
}
