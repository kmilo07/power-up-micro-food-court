package com.pragma.powerup.usermicroservice.domain.api;

import com.pragma.powerup.usermicroservice.domain.model.Category;

import java.util.List;

public interface ICategoryServicePort {

    List<Category> getAllCategories();
}
