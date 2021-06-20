package org.lisasp.alphatimer.livetiming.data.service;

import org.lisasp.alphatimer.livetiming.data.entity.Heat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class HeatService extends CrudService<Heat, Integer> {

    private HeatRepository repository;

    public HeatService(@Autowired HeatRepository repository) {
        this.repository = repository;
    }

    @Override
    protected HeatRepository getRepository() {
        return repository;
    }
}
