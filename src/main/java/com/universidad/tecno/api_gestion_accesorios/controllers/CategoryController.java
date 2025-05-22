package com.universidad.tecno.api_gestion_accesorios.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.universidad.tecno.api_gestion_accesorios.entities.Category;
import com.universidad.tecno.api_gestion_accesorios.services.interfaces.CategoryService;

@CrossOrigin(origins = { "http://localhost:4500" })
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/page/{page}")
    public ResponseEntity<?> listPageable(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 7);
        Page<Category> categories = categoryService.paginateAll(pageable);
        return ResponseEntity.ok(categories);
    }
    @GetMapping
    public List<Category> getCategories() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id) {
        return categoryService.findById(id)
                .map(category -> ResponseEntity.ok(category))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        // No asignar el id, dejar que la base de datos lo maneje automáticamente
        category.setId(null); // En caso de que el id haya sido asignado de manera incorrecta
        Category newCategory = categoryService.save(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        return categoryService.update(id, category)
                .map(updatedCategory -> ResponseEntity.ok().body(updatedCategory))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable Long id) {
        boolean deleted = categoryService.deleteById(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
