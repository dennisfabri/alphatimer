package org.lisasp.alphatimer.test.datatests.testdoubles;

import lombok.RequiredArgsConstructor;
import org.lisasp.basics.jre.date.DateTimeFacade;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class DateTimeFacadeTestDouble implements DateTimeFacade {

    private final LocalDateTime NOW;

    @Override
    public LocalDateTime now() {
        return NOW;
    }
}
