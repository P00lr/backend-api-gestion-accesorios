package com.universidad.tecno.api_gestion_accesorios.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.universidad.tecno.api_gestion_accesorios.dto.accessory.AccessoryAssignmentCategoryDto;
import com.universidad.tecno.api_gestion_accesorios.dto.accessory.AccessoryWithCategoryDto;
import com.universidad.tecno.api_gestion_accesorios.entities.Accessory;

public interface AccessoryService {
    List<Accessory> findAll();
    Optional<AccessoryWithCategoryDto> findById(Long id); //muestra con dto
    Accessory save(Accessory accessory);
    Accessory saveDto(AccessoryAssignmentCategoryDto dto);
    Optional<Accessory> update(Long id, Accessory accessory);
    boolean deleteById(Long id);
    List<AccessoryWithCategoryDto> getAccessoryWithCategoryDtos();
}
