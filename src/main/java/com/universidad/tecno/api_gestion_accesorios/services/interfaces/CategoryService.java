package com.universidad.tecno.api_gestion_accesorios.services.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.universidad.tecno.api_gestion_accesorios.entities.Category;

public interface CategoryService {
    Page<Category> paginateAll(Pageable pageable);

    List<Category> findAll();

    Optional<Category> findById(Long id);

    Category save(Category category);

    Optional<Category> update(Long id, Category category);

    boolean deleteById(Long id);
}
