package com.universidad.tecno.api_gestion_accesorios.services.interfaces;

public interface ViewCounterService {
    public Long incrementView(String page);
    public Long getViews(String page);
}
