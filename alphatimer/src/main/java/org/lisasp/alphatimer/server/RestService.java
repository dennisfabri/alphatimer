package org.lisasp.alphatimer.server;

import lombok.RequiredArgsConstructor;
import org.lisasp.alphatimer.legacy.dto.Heat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestService {

    private final SerialInterpreter interpreter;

    @GetMapping("/legacy/heats.xml")
    public String getLegacyHeats() {
        return interpreter.getLegacyDataXML();
    }

    @GetMapping("/legacy/heats.json")
    public Heat[] getLegacyHeatsJson() {
        return interpreter.getLegacyData();
    }
}
