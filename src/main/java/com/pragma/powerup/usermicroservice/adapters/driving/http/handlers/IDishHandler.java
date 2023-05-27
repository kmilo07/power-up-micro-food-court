package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.UpdateDishRequestDto;

public interface IDishHandler {
    void createDish(DishRequestDto dishRequestDto);

    void updateDish(UpdateDishRequestDto updateDishRequestDto);
}
