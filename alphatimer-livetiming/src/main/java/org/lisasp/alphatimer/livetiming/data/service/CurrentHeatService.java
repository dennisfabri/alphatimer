package org.lisasp.alphatimer.livetiming.data.service;

import lombok.Getter;
import org.lisasp.alphatimer.livetiming.data.entity.Heat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CurrentHeatService {

    private HeatRepository repository;

    @Getter
    private Heat currentHeat = new Heat(3, 14);

    private List<Consumer<Heat>> listeners = new ArrayList<>();

    public CurrentHeatService(@Autowired HeatRepository repository) {
        this.repository = repository;

        new Thread(() -> {
            while (true) {
                currentHeat.setHeat(currentHeat.getHeat() + 1);
                notifyListeners();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void notifyListeners() {
        listeners.forEach(l -> l.accept(currentHeat));
    }

    public void subscribe(Consumer<Heat> listener) {
        listeners.add(listener);
    }
}
