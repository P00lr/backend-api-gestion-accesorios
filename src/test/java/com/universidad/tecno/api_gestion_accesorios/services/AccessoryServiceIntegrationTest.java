package com.universidad.tecno.api_gestion_accesorios.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.universidad.tecno.api_gestion_accesorios.dto.accessory.AccessoryAssignmentCategoryDto;
import com.universidad.tecno.api_gestion_accesorios.dto.accessory.AccessoryWithCategoryDto;
import com.universidad.tecno.api_gestion_accesorios.entities.Accessory;
import com.universidad.tecno.api_gestion_accesorios.entities.Category;
import com.universidad.tecno.api_gestion_accesorios.repositories.AccessoryRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.CategoryRepository;
import com.universidad.tecno.api_gestion_accesorios.services.interfaces.AccessoryService;

@SpringBootTest
@Transactional
public class AccessoryServiceIntegrationTest {
    @Autowired
    private AccessoryService accessoryService;

    @Autowired
    private AccessoryRepository accessoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Category testCategory;

    @BeforeEach
    void setUp() {
        // Limpiar las tablas antes de cada prueba
        accessoryRepository.deleteAll();
        categoryRepository.deleteAll();

        // Crear categoría de prueba
        testCategory = new Category();
        testCategory.setName("Categoría Test");
        categoryRepository.save(testCategory);
    }

    @Test
    void testSaveAndFindAccessory() {
        Accessory accessory = new Accessory();
        accessory.setName("Accesorio Test");
        accessory.setPrice(100.0);
        accessory.setDescription("Descripción de prueba");
        accessory.setCategory(testCategory);

        Accessory saved = accessoryService.save(accessory);

        Optional<Accessory> found = accessoryRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Accesorio Test", found.get().getName());
        assertEquals(testCategory.getId(), found.get().getCategory().getId());
    }

    @Test
    void testDeleteAccessory() {
        Accessory accessory = new Accessory();
        accessory.setName("Accesorio para borrar");
        accessory.setPrice(50.0);
        accessory.setDescription("Descripción para borrar");
        accessory.setCategory(testCategory);

        Accessory saved = accessoryService.save(accessory);

        boolean deleted = accessoryService.deleteById(saved.getId());
        assertTrue(deleted);

        Optional<Accessory> found = accessoryRepository.findById(saved.getId());
        assertFalse(found.isPresent());
    }

    @Test
    void testUpdateAccessory() {
        // Arrange
        Category originalCategory = new Category();
        originalCategory.setName("Audio");
        categoryRepository.save(originalCategory);

        Accessory accessory = new Accessory();
        accessory.setName("Auriculares");
        accessory.setPrice(100.0);
        accessory.setDescription("Auriculares de prueba");
        accessory.setBrand("Sony");
        accessory.setModel("X100");
        accessory.setCategory(originalCategory);
        accessory = accessoryRepository.save(accessory);

        Category newCategory = new Category();
        newCategory.setName("Gaming");
        categoryRepository.save(newCategory);

        AccessoryAssignmentCategoryDto updateDto = new AccessoryAssignmentCategoryDto();
        updateDto.setName("Auriculares Pro");
        updateDto.setPrice(150.0);
        updateDto.setDescription("Auriculares Pro actualizados");
        updateDto.setBrand("Sony");
        updateDto.setModel("X200");
        updateDto.setCategoryId(newCategory.getId());

        // Act
        Optional<AccessoryWithCategoryDto> updated = accessoryService.update(accessory.getId(), updateDto);

        // Assert
        assertTrue(updated.isPresent());
        AccessoryWithCategoryDto dto = updated.get();
        assertEquals("Auriculares Pro", dto.getName());
        assertEquals(150.0, dto.getPrice());
        assertEquals("Auriculares Pro actualizados", dto.getDescription());
        assertEquals("Sony", dto.getBrand());
        assertEquals("X200", dto.getModel());
        assertEquals("Gaming", dto.getCategoryName());
    }

    @Test
    void testPaginateAll() {
        // Arrange
        Category category1 = new Category();
        category1.setName("Accesorios");
        categoryRepository.save(category1);

        for (int i = 1; i <= 5; i++) {
            Accessory accessory = new Accessory();
            accessory.setName("Accesorio " + i);
            accessory.setPrice(50.0 + i);
            accessory.setDescription("Desc " + i);
            accessory.setBrand("Marca" + i);
            accessory.setModel("Modelo" + i);
            accessory.setCategory(category1);
            accessoryRepository.save(accessory);
        }

        Pageable pageable = PageRequest.of(0, 3); // Página 0, tamaño 3

        // Act
        Page<AccessoryWithCategoryDto> page = accessoryService.paginateAll(pageable);

        // Assert
        assertEquals(3, page.getContent().size());
        assertEquals(5, page.getTotalElements()); // Total en la base
    }

}
