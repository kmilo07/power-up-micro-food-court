package com.pragma.powerup.usermicroservice.domain.spi;

import com.pragma.powerup.usermicroservice.domain.model.Dish;

public interface IDishPersistencePort {
    Dish getDishById(Long dishId);

    void saveDish(Dish dish);
}
