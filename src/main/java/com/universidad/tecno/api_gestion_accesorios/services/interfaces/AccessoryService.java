package com.universidad.tecno.api_gestion_accesorios.services.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.universidad.tecno.api_gestion_accesorios.dto.accessory.AccessoryAssignmentCategoryDto;
import com.universidad.tecno.api_gestion_accesorios.dto.accessory.AccessoryWithCategoryDto;
import com.universidad.tecno.api_gestion_accesorios.dto.accessory.GetAccessories;
import com.universidad.tecno.api_gestion_accesorios.entities.Accessory;

public interface AccessoryService {

    public Page<GetAccessories> paginateAccessoriesCatalog(Pageable pageable);

    List<Accessory> findAll();

    Page<AccessoryWithCategoryDto> paginateAll(Pageable pageable);

    Optional<AccessoryWithCategoryDto> findById(Long id);

    Accessory save(Accessory accessory);

    Accessory saveDto(AccessoryAssignmentCategoryDto dto);

    Optional<AccessoryWithCategoryDto> update(Long id, AccessoryAssignmentCategoryDto dto);

    boolean deleteById(Long id);

    List<AccessoryWithCategoryDto> getAccessoryWithCategoryDtos();
}
