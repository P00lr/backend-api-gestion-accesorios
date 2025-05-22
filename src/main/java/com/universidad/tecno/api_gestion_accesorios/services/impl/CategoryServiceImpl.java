package com.universidad.tecno.api_gestion_accesorios.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.universidad.tecno.api_gestion_accesorios.entities.Category;
import com.universidad.tecno.api_gestion_accesorios.repositories.CategoryRepository;
import com.universidad.tecno.api_gestion_accesorios.services.interfaces.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Page<Category> paginateAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public List<Category> findAll() {
        return (List<Category>) categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Optional<Category> update(Long id, Category category) {
        return categoryRepository.findById(id)
                .map(existingCategory -> {
                    if (category.getName() != null) {
                        existingCategory.setName(category.getName());
                    }
                    return categoryRepository.save(existingCategory);
                });
    }

    @Override
    public boolean deleteById(Long id) {
        Optional<Category> deletedOp = categoryRepository.findById(id);
        if (deletedOp.isPresent()) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
