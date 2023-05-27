package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.CategoryNotFoundException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.NoDataFoundException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.ICategoryEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.ICategoryRepository;
import com.pragma.powerup.usermicroservice.domain.model.Category;
import com.pragma.powerup.usermicroservice.domain.spi.ICategoryPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CategoryMysqlAdapter implements ICategoryPersistencePort {
    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;

    @Override
    public List<Category> getAllCategories() {
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();
        if (categoryEntityList.isEmpty()){
            throw new NoDataFoundException();
        }
        return categoryEntityMapper.toCategoryList(categoryEntityList);
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(categoryId);
        if (categoryEntity.isEmpty()){
            throw new CategoryNotFoundException();
        }
        return categoryEntity.map(categoryEntityMapper::toCategory).orElse(null);
    }
}
