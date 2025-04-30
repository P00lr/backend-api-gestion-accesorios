package com.universidad.tecno.api_gestion_accesorios.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.universidad.tecno.api_gestion_accesorios.dto.accessory.AccessoryAssignmentCategoryDto;
import com.universidad.tecno.api_gestion_accesorios.dto.accessory.AccessoryWithCategoryDto;
import com.universidad.tecno.api_gestion_accesorios.entities.Accessory;
import com.universidad.tecno.api_gestion_accesorios.entities.Category;
import com.universidad.tecno.api_gestion_accesorios.repositories.AccessoryRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.CategoryRepository;
import com.universidad.tecno.api_gestion_accesorios.services.interfaces.AccessoryService;

@Service
public class AccessoryServiceImpl implements AccessoryService {

    @Autowired
    private AccessoryRepository accessoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Accessory> findAll() {
        return (List<Accessory>) accessoryRepository.findAll();
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
    public Optional<Accessory> update(Long id, Accessory accessory) {
        return accessoryRepository.findById(id)
                .map(existingAccessory -> {
                    if (accessory.getName() != null) {
                        existingAccessory.setName(accessory.getName());
                    }
                    if (accessory.getPrice() != null) {
                        existingAccessory.setPrice(accessory.getPrice());
                    }
                    if (accessory.getDescription() != null) {
                        existingAccessory.setDescription(accessory.getDescription());
                    }
                    if (accessory.getBrand() != null) {
                        existingAccessory.setBrand(accessory.getBrand());
                    }
                    if (accessory.getModel() != null) {
                        existingAccessory.setModel(accessory.getModel());
                    }
                    return accessoryRepository.save(existingAccessory);
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
