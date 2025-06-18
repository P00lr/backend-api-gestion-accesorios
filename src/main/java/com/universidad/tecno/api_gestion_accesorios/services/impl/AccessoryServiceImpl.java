package com.universidad.tecno.api_gestion_accesorios.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.universidad.tecno.api_gestion_accesorios.dto.accessory.AccessoryAssignmentCategoryDto;
import com.universidad.tecno.api_gestion_accesorios.dto.accessory.AccessoryWithCategoryDto;
import com.universidad.tecno.api_gestion_accesorios.dto.accessory.GetAccessories;
import com.universidad.tecno.api_gestion_accesorios.entities.Accessory;
import com.universidad.tecno.api_gestion_accesorios.entities.Category;
import com.universidad.tecno.api_gestion_accesorios.repositories.AccessoryRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.CategoryRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.WarehouseDetailRepository;
import com.universidad.tecno.api_gestion_accesorios.services.interfaces.AccessoryService;

@Service
public class AccessoryServiceImpl implements AccessoryService {

    @Autowired
    private AccessoryRepository accessoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private WarehouseDetailRepository warehouseDetailRepository;

    // catalogo paginado solo para el client
    @Transactional(readOnly = true)
    @Override
    public Page<GetAccessories> paginateAccessoriesCatalog(Pageable pageable) {
        return this.accessoryRepository.findAllWithAvailableStock(pageable)
                .map(accessory -> {
                    Integer totalStock = warehouseDetailRepository
                            .sumStockByAccessoryIdAndState(accessory.getId(), "AVAILABLE");

                    GetAccessories dto = new GetAccessories();
                    dto.setId(accessory.getId());
                    dto.setName(accessory.getName());
                    dto.setDescription(accessory.getDescription());
                    dto.setPrice(accessory.getPrice());
                    dto.setStock(totalStock != null ? totalStock : 0);
                    dto.setAvailable(true); // Ya se que tiene stock
                    dto.setCategoryId(accessory.getCategory().getId());
                    dto.setCategoryName(accessory.getCategory().getName());

                    return dto;
                });
    }

    @Override
    public List<Accessory> findAll() {
        return (List<Accessory>) accessoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<AccessoryWithCategoryDto> paginateAll(Pageable pageable) {
        return this.accessoryRepository.findAll(pageable)
                .map(accessory -> {
                    AccessoryWithCategoryDto dto = new AccessoryWithCategoryDto();
                    dto.setId(accessory.getId());
                    dto.setName(accessory.getName());
                    dto.setPrice(accessory.getPrice());
                    dto.setDescription(accessory.getDescription());
                    dto.setBrand(accessory.getBrand());
                    dto.setModel(accessory.getModel());
                    dto.setCategoryId(accessory.getCategory().getId());
                    dto.setCategoryName(accessory.getCategory().getName());
                    return dto;
                });
    }

    @Override
    public Optional<AccessoryWithCategoryDto> findById(Long id) {

        return accessoryRepository.findById(id).map(accessory -> {
            AccessoryWithCategoryDto accessoryDto = new AccessoryWithCategoryDto();
            accessoryDto.setId(accessory.getId());
            accessoryDto.setName(accessory.getName());
            accessoryDto.setPrice(accessory.getPrice());
            accessoryDto.setDescription(accessory.getDescription());
            accessoryDto.setBrand(accessory.getBrand());
            accessoryDto.setModel(accessory.getModel());
            accessoryDto.setCategoryId(accessory.getCategory().getId());
            accessoryDto.setCategoryName(accessory.getCategory().getName());

            return accessoryDto;
        });

    }

    @Override
    public Accessory save(Accessory accessory) {
        return accessoryRepository.save(accessory);
    }

    @Override
    public Accessory saveDto(AccessoryAssignmentCategoryDto dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id: " + dto.getCategoryId()));

        Accessory accessory = new Accessory();

        accessory.setName(dto.getName());
        accessory.setPrice(dto.getPrice());
        accessory.setDescription(dto.getDescription());
        accessory.setBrand(dto.getBrand());
        accessory.setModel(dto.getModel());
        accessory.setCategory(category);

        return accessoryRepository.save(accessory);
    }

    @Override
    public Optional<AccessoryWithCategoryDto> update(Long id, AccessoryAssignmentCategoryDto dto) {
        return accessoryRepository.findById(id)
                .map(existingAccessory -> {
                    if (dto.getName() != null) {
                        existingAccessory.setName(dto.getName());
                    }
                    if (dto.getPrice() != null) {
                        existingAccessory.setPrice(dto.getPrice());
                    }
                    if (dto.getDescription() != null) {
                        existingAccessory.setDescription(dto.getDescription());
                    }
                    if (dto.getBrand() != null) {
                        existingAccessory.setBrand(dto.getBrand());
                    }
                    if (dto.getModel() != null) {
                        existingAccessory.setModel(dto.getModel());
                    }
                    if (dto.getCategoryId() != null) {
                        categoryRepository.findById(dto.getCategoryId())
                                .ifPresent(existingAccessory::setCategory);
                    }

                    accessoryRepository.save(existingAccessory);

                    // Convertimos el resultado actualizado a DTO de respuesta
                    AccessoryWithCategoryDto responseDto = new AccessoryWithCategoryDto();
                    responseDto.setId(existingAccessory.getId());
                    responseDto.setName(existingAccessory.getName());
                    responseDto.setPrice(existingAccessory.getPrice());
                    responseDto.setDescription(existingAccessory.getDescription());
                    responseDto.setBrand(existingAccessory.getBrand());
                    responseDto.setModel(existingAccessory.getModel());
                    dto.setCategoryId(existingAccessory.getCategory().getId());
                    responseDto.setCategoryName(existingAccessory.getCategory().getName());

                    return responseDto;
                });
    }

    @Override
    public boolean deleteById(Long id) {
        Optional<Accessory> accessoryOp = accessoryRepository.findById(id);
        if (accessoryOp.isPresent()) {
            accessoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<AccessoryWithCategoryDto> getAccessoryWithCategoryDtos() {
        List<Accessory> accessories = (List<Accessory>) accessoryRepository.findAll();

        return accessories.stream().map(accessory -> {
            String category = accessory.getCategory().getName();
            return new AccessoryWithCategoryDto(accessory.getId(),
                    accessory.getName(),
                    accessory.getPrice(),
                    accessory.getDescription(),
                    accessory.getBrand(),
                    accessory.getModel(),
                    category);
        }).collect(Collectors.toList());
    }

}
