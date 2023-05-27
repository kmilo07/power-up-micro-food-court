package com.pragma.powerup.usermicroservice.domain.api;

import com.pragma.powerup.usermicroservice.domain.model.Dish;

public interface IDishServicePort {
    void createDish(Dish requestToDish);

    void updateDish(Dish requestToDish);
}
