package de.dennisfabri.alphatimer.storage;

import java.time.LocalDate;

public class ActualDate implements DateFacade {

    @Override
    public LocalDate today() {
        return LocalDate.now();
    }
}
