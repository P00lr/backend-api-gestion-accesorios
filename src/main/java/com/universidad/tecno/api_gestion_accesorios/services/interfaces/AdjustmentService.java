package com.universidad.tecno.api_gestion_accesorios.services.interfaces;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.universidad.tecno.api_gestion_accesorios.dto.adjustment.CreateAdjustmentDto;
import com.universidad.tecno.api_gestion_accesorios.dto.adjustment.GetAdjustmentDto;
import com.universidad.tecno.api_gestion_accesorios.dto.adjustment.ListAdjustmentDto;
import com.universidad.tecno.api_gestion_accesorios.entities.Adjustment;

public interface AdjustmentService {
    
    public Page<ListAdjustmentDto> paginateAll(Pageable pageable);
    public List<ListAdjustmentDto> findAll();
    public GetAdjustmentDto getAdjustmentById(Long id);
    public Adjustment createAdjustment(CreateAdjustmentDto dto);    
    boolean deleteById(Long id); 
}
