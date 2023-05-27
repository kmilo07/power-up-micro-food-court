package com.pragma.powerup.usermicroservice.domain.spi;

import com.pragma.powerup.usermicroservice.domain.model.Dish;

public interface IDishPersistencePort {
    void createDish(Dish dish);

    void updateDish(Dish dish);
}
