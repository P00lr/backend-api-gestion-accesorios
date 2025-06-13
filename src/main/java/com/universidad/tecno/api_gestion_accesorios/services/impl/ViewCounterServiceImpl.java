package com.universidad.tecno.api_gestion_accesorios.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.universidad.tecno.api_gestion_accesorios.entities.ViewCounter;
import com.universidad.tecno.api_gestion_accesorios.repositories.ViewCounterRepository;
import com.universidad.tecno.api_gestion_accesorios.services.interfaces.ViewCounterService;

@Service
public class ViewCounterServiceImpl implements ViewCounterService{

    @Autowired
    private ViewCounterRepository viewCounterRepository;

    public Long incrementView(String page) {
        ViewCounter counter = viewCounterRepository.findById(page)
                .orElse(new ViewCounter());
        counter.setPage(page);
        counter.setViews(counter.getViews() == null ? 1 : counter.getViews() + 1);
        viewCounterRepository.save(counter);
        return counter.getViews();
    }

    public Long getViews(String page) {
        return viewCounterRepository.findById(page)
                .map(ViewCounter::getViews)
                .orElse(0L);
    }
}
